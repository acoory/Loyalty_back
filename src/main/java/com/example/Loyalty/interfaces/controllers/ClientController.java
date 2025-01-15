package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.domain.entities.Client;
import com.example.Loyalty.domain.repositories.ClientRepository;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import com.example.Loyalty.usecases.ClientUseCase;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientUseCase clientUseCase;

    private final ClientRepository clientRepository;

    public ClientController(ClientUseCase clientUseCase, ClientRepository clientRepository) {
        this.clientUseCase = clientUseCase;
        this.clientRepository = clientRepository;
    }


    @PostMapping("/create")
    public Client newEmployee(@RequestBody Client newClient) {
        System.out.println("newEnterprise = " + newClient);
        try {
            return clientUseCase.addClient(newClient);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public Iterable<Client> getAllEnterprises() {
        System.out.println("getAllEnterprises");

        return clientUseCase.getAllClient("6786e3c53e29483b9fe4b9b1");
    }
}
