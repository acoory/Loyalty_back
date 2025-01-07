package com.example.Loyalty.domain.entities;
import com.example.Loyalty.domain.entities.Client;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Enterprise {

    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    public Enterprise() {}

    public Enterprise(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
