package com.example.Loyalty.infrastructure.repositories;


import com.example.Loyalty.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client save(Client client);

    Optional<Client> findById(Long id);

    Optional<Client> findByEmail(String client);

    void delete(Client client);

    List<Client> findAll();
}
