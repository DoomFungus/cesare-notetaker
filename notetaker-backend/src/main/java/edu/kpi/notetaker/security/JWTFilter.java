package edu.kpi.notetaker.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.kpi.notetaker.exceptionhandling.ErrorMessage;
import edu.kpi.notetaker.exceptionhandling.ExceptionHandlerAdvice;
import edu.kpi.notetaker.exceptionhandling.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTProvider provider;
    private final ExceptionHandlerAdvice advice;

    @Autowired
    public JWTFilter(JWTProvider provider, ExceptionHandlerAdvice advice) {
        this.provider = provider;
        this.advice = advice;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        if (token != null) {
            try{
                Authentication auth = provider.getAuthentication(provider.parseClaims(token));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex){
                ErrorMessage message = new ErrorMessage(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,
                        "Access token expired", request.getRequestURI());

                response.setStatus(message.getStatus());
                response.setContentType("application/json");

                ObjectMapper mapper = new ObjectMapper();
                PrintWriter out = response.getWriter();
                out.print(mapper.writeValueAsString(message));
                out.flush();
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/signin") || path.equals("/signup") || path.equals("/refresh");
    }

    private String resolveToken(HttpServletRequest req) {
        return req.getHeader("Authorization");
    }
}
