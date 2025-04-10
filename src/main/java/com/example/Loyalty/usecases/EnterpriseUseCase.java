package com.example.Loyalty.usecases;

import com.example.Loyalty.config.SecurityConfig;
import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.infrastructure.repositories.EnterpriseRepository;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseUseCase {

    private final EnterpriseRepository enterpriseRepository;
    private final SecurityConfig securityConfig;


    @Autowired
    public EnterpriseUseCase(EnterpriseRepository enterpriseRepository, SecurityConfig securityConfig) {
        this.enterpriseRepository = enterpriseRepository;
        this.securityConfig = securityConfig;
    }

//    public Enterprise login(Enterprise enterprise) {
//        try {
//            String email = enterprise.getEmail();
//            String password = enterprise.getPassword();
//
//            if(email == null || password == null) {
//                throw new EnterpriseException("Email and password are required");
//            }
//
//            // Récupérer l'entreprise
//            Enterprise foundEnterprise = enterpriseRepository.findByEmail(email)
//                    .orElseThrow(() -> new EnterpriseException("Enterprise not found"));
//
//            // Logs pour le débogage
//            System.out.println("########### DEBUG LOGIN ###########");
//            System.out.println("Email fourni: " + email);
//            System.out.println("Mot de passe fourni: " + password);
//            System.out.println("Mot de passe stocké en DB: " + foundEnterprise.getPassword());
//            System.out.println("################################");
//
//            // Vérifier le mot de passe avec BCrypt
//            if (!BCrypt.checkpw(password, foundEnterprise.getPassword())) {
//                throw new EnterpriseException("Invalid password");
//            }
//
//            foundEnterprise.setPassword(null);
//
//            // return data enterprise
//
//
//            return foundEnterprise;
//        } catch (Exception e) {
//            throw new EnterpriseException("error : " + e.getMessage());
//        }
//    }

    public Enterprise createEnterprise(Enterprise newEnterprise) {

        try {
        String name = newEnterprise.getName();

        if(name == null) {
            throw new EnterpriseException("Name is required");
        }

        return enterpriseRepository.save(newEnterprise);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

}
