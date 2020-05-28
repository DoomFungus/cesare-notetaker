package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.TokenExpiredException;
import edu.kpi.notetaker.message.AuthOutputMessage;
import edu.kpi.notetaker.model.Role;
import edu.kpi.notetaker.model.User;
import edu.kpi.notetaker.security.JWTProvider;
import edu.kpi.notetaker.service.AuthService;
import edu.kpi.notetaker.service.RoleService;
import edu.kpi.notetaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JWTProvider jwtProvider,
                           PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public AuthOutputMessage signIn(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        String refreshToken = jwtProvider.createRefreshToken(username);
        userService.updateUserToken(username, refreshToken);
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AuthOutputMessage message = new AuthOutputMessage();
        message.setAccessToken(jwtProvider.createAccessToken(username));
        message.setRefreshToken(refreshToken);
        return message;
    }

    @Override
    public AuthOutputMessage signUp(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName("USER"));
        user.setRoles(roles);
        userService.createUser(user);

        return signIn(username, password);
    }

    @Override
    public String refresh(String refreshToken) {
        String claimedUsername = jwtProvider.getSubject(jwtProvider.parseClaims(refreshToken));
        if(!refreshToken.equals(userService.findByUsername(claimedUsername).getRefreshToken()))
            throw new TokenExpiredException("Refresh token expired");
        return jwtProvider.createAccessToken(claimedUsername);
    }

    @Override
    public void signout() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        userService.updateUserToken(user.getUsername(), null);
    }
}
