package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.domain.entities.Admin;
import com.example.Loyalty.domain.entities.Client;
import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.domain.entities.LoyaltyCard;
import com.example.Loyalty.infrastructure.gmail.GmailService;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import com.example.Loyalty.payloads.ClientControllerCreateRequest;
import com.example.Loyalty.security.RequireJwt;
import com.example.Loyalty.usecases.AdminUseCase;
import com.example.Loyalty.usecases.ClientUseCase;
import com.example.Loyalty.usecases.EnterpriseUseCase;
import com.example.Loyalty.usecases.LoyaltyUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/loyalty")
public class LoyaltyController {
    private final LoyaltyUseCase loyaltyUseCase;

    LoyaltyController(LoyaltyUseCase loyaltyUseCase) {
        this.loyaltyUseCase = loyaltyUseCase;
    }


    @PutMapping("/update")
    @RequireJwt
    public Object update(@RequestBody ClientControllerCreateRequest newClient, HttpServletRequest request) {
        try {
            String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

            System.out.println("###### Authenticated user: " + authenticatedUsername);
            System.out.println("Authenticated user: " + authenticatedUsername);
            loyaltyUseCase.updateLoyaltyCard(authenticatedUsername, newClient.getClient().getId());


            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("admin", "john");

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

}
