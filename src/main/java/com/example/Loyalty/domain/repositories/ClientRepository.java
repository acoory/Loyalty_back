package com.example.Loyalty.domain.repositories;
import com.example.Loyalty.domain.entities.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(String id);
    List<Client> findAll();
    void deleteById(String id);
    List<Client> findByEnterpriseId(String enterpriseId);
}
