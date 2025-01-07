package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.domain.repositories.EnterpriseRepository;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import com.example.Loyalty.usecases.AddEnterpriseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;


@RestController
public class EnterpriseController {

    private final AddEnterpriseUseCase addEnterpriseUseCase;

    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseController(AddEnterpriseUseCase addEnterpriseUseCase, EnterpriseRepository enterpriseRepository) {
        this.addEnterpriseUseCase = addEnterpriseUseCase;
        this.enterpriseRepository = enterpriseRepository;
    }


    @PostMapping("/create")
    Enterprise newEmployee(@RequestBody Enterprise newEnterprise) {

        try {
        return addEnterpriseUseCase.addEnterprise(newEnterprise);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

}
