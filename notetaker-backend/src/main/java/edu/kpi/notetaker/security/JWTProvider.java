package edu.kpi.notetaker.security;

import edu.kpi.notetaker.exceptionhandling.exceptions.TokenExpiredException;
import edu.kpi.notetaker.model.User;
import edu.kpi.notetaker.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource(value = {"classpath:jwt.properties"})
public class JWTProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.access.validity}")
    private long accessValidityInMilliseconds;
    @Value("${jwt.refresh.validity}")
    private long refreshValidityInMilliseconds;

    private final UserService userService;

    @Autowired
    public JWTProvider(UserService userService) {
        this.userService = userService;
    }

    public Authentication getAuthentication(Jws<Claims> claims){
        User user = userService.findByUsername(getSubject(claims));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public String createAccessToken(String username){
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + accessValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String createRefreshToken(String username){
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getSubject(Jws<Claims> claims){
        return claims.getBody().getSubject();
    }

    public Jws<Claims> parseClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    }
}
