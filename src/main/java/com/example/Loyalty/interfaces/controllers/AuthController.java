package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.config.JwtUtil;
import com.example.Loyalty.domain.entities.Admin;
import com.example.Loyalty.infrastructure.repositories.AdminRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );


        String jwt = jwtUtil.generateToken(loginRequest.getEmail());

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Pour HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 24 heures en secondes

        response.addCookie(cookie);

        Admin AdminRepo = adminRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin not found with email: " + loginRequest.getEmail()));

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Connexion réussie");

        AdminRepo.setPassword(null); // Ne pas envoyer le mot de passe dans la réponse
        // include object enterprise in admin

        // send all information about the user
        responseBody.put("admin", AdminRepo);



        return ResponseEntity.ok(responseBody);
    }
}
