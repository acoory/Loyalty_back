package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.domain.entities.Admin;
import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import com.example.Loyalty.usecases.AdminUseCase;
import com.example.Loyalty.usecases.EnterpriseUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminUseCase adminUseCase;


    public AdminController(AdminUseCase adminUseCase) {
        this.adminUseCase = adminUseCase;
    }

    @PostMapping("/create")
    public Admin create(@RequestBody Admin newAdmin) {
        System.out.println("Create Admin = " + newAdmin);
        try {
            return adminUseCase.createAdmin(newAdmin);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }
}
