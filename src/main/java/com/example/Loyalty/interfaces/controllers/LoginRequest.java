package com.example.Loyalty.interfaces.controllers;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
