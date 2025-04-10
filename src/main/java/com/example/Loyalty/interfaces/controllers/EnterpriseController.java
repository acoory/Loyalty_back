package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.domain.entities.Enterprise;
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
    public Enterprise create(@RequestBody Enterprise newEnterprise) {
        System.out.println("Create enterprise = " + newEnterprise);
        try {
            return enterpriseUseCase.createEnterprise(newEnterprise);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }
//
//    @PostMapping("/login")
//    public Enterprise login(@RequestBody Enterprise enterprise) {
//        System.out.println("enterprise = " + enterprise);
//        try {
////            return enterprise.getEmail() + " " + enterprise.getPassword();
//            return enterpriseUseCase.login(enterprise);
//        } catch (Exception e) {
//            throw new EnterpriseException("error : " + e.getMessage());
//        }
//    }

//    @GetMapping("/all")
//    public Iterable<Enterprise> getAllEnterprises() {
//        System.out.println("getAllEnterprises");
//        return enterpriseUseCase.getAllEnterprises();
//    }
}
