package com.example.Loyalty.usecases;

import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.domain.repositories.EnterpriseRepository;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Loyalty.config.SecurityConfig;

@Service
public class AddEnterpriseUseCase {

    private final EnterpriseRepository enterpriseRepository;


    @Autowired
    public AddEnterpriseUseCase(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    public Enterprise addEnterprise(Enterprise newEnterprise) {

        try {
        String name = newEnterprise.getName();
        String email = newEnterprise.getEmail();
        String password = SecurityConfig.hashPassword(newEnterprise.getPassword());

        if(name == null || email == null || password == null) {
            throw new EnterpriseException("Name, email and password are required");
        }

        newEnterprise.setPassword(password);

        return enterpriseRepository.save(newEnterprise);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }

    }
}
