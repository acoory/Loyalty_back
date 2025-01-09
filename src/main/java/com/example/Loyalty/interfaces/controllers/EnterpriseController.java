package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.domain.repositories.EnterpriseRepository;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import com.example.Loyalty.usecases.AddEnterpriseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;


@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    private final AddEnterpriseUseCase addEnterpriseUseCase;

    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseController(AddEnterpriseUseCase addEnterpriseUseCase, EnterpriseRepository enterpriseRepository) {
        this.addEnterpriseUseCase = addEnterpriseUseCase;
        this.enterpriseRepository = enterpriseRepository;
    }


    @PostMapping("/create")
    public Enterprise newEmployee(@RequestBody Enterprise newEnterprise) {
        System.out.println("newEnterprise = " + newEnterprise);
        try {
        return addEnterpriseUseCase.addEnterprise(newEnterprise);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public Iterable<Enterprise> getAllEnterprises() {
        System.out.println("getAllEnterprises");
        return null;
    }
}
