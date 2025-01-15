package com.example.Loyalty.usecases;
import com.example.Loyalty.domain.entities.Client;
import com.example.Loyalty.domain.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUseCase {
    private final ClientRepository clientRepository;


    @Autowired
    public ClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client addClient(Client newClient) {

        System.out.println("newClient = " + newClient);

        String firstname = newClient.getFirstName();
        String lastname = newClient.getLastName();
        String email = newClient.getEmail();
        String enterpriseId = newClient.getEnterpriseId();


        Client client = new Client(firstname, lastname, email, enterpriseId);
        return clientRepository.save(client);
    }

    public Iterable<Client> getAllClient(String enterpriseId) {
        return clientRepository.findByEnterpriseId(enterpriseId);
    }



}
