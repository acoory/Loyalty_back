package com.example.Loyalty.security;

import com.example.Loyalty.config.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;

@Aspect
@Component
public class JwtAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Around("@annotation(requireJwt) || @within(requireJwt)")
    public Object checkJwt(ProceedingJoinPoint joinPoint, RequireJwt requireJwt) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        
        String token = extractJwtFromRequest(request);
        
        if (token != null && jwtUtil.validateToken(token)) {
            // Extraire le nom d'utilisateur du token
            String username = jwtUtil.extractUsername(token);
            
            // Créer une authentification
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("USER"))
            );
            
            // Stocker l'authentification dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            return joinPoint.proceed();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT invalide ou expiré");
        }
    }
    
    private String extractJwtFromRequest(HttpServletRequest request) {
        // Essayer d'extraire le JWT des cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        
        // Essayer d'extraire le JWT du header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        
        return null;
    }
} 