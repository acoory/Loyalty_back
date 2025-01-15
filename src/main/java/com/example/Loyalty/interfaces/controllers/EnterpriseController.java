package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.domain.repositories.EnterpriseRepository;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import com.example.Loyalty.usecases.EnterpriseUseCase;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    private final EnterpriseUseCase enterpriseUseCase;


    public EnterpriseController(EnterpriseUseCase enterpriseUseCase) {
        this.enterpriseUseCase = enterpriseUseCase;
    }


    @PostMapping("/create")
    public Enterprise newEmployee(@RequestBody Enterprise newEnterprise) {
        System.out.println("newEnterprise = " + newEnterprise);
        try {
        return enterpriseUseCase.addEnterprise(newEnterprise);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public Iterable<Enterprise> getAllEnterprises() {
        System.out.println("getAllEnterprises");
        return enterpriseUseCase.getAllEnterprises();
    }
}
