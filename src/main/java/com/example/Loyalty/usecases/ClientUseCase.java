package com.example.Loyalty.usecases;

import com.example.Loyalty.config.SecurityConfig;
import com.example.Loyalty.domain.entities.Admin;
import com.example.Loyalty.domain.entities.Client;
import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.infrastructure.repositories.ClientRepository;
import com.example.Loyalty.infrastructure.repositories.EnterpriseRepository;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientUseCase {
    private final ClientRepository clientRepository;
    private final SecurityConfig securityConfig;

    @Autowired
    public ClientUseCase(ClientRepository clientRepository, SecurityConfig securityConfig) {
        this.clientRepository = clientRepository;
        this.securityConfig = securityConfig;
    }

    public Client isClientExist(String email) {
        try {
            return clientRepository.findByEmail(email).orElse(null);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

    public Client createClient(Client newCLient) {
        try {
            String firstname = newCLient.getFirstname();
            String lastname = newCLient.getLastname();
            String email = newCLient.getEmail();

            if (firstname == null || lastname == null || email == null) {
                throw new EnterpriseException("All fields are required");
            }

            clientRepository.findByEmail(email)
                    .ifPresent(client -> {
                        throw new EnterpriseException("Email already exists");
                    });

            return clientRepository.save(newCLient);
        }
        catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }
}
