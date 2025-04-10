package com.example.Loyalty.infrastructure.repositories;


import com.example.Loyalty.domain.entities.Client;
import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.domain.entities.LoyaltyCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LoyaltyRepository extends JpaRepository<LoyaltyCard, Long> {
    LoyaltyCard save(LoyaltyCard loyaltyCard);

    Optional<LoyaltyCard> findById(Long id);

    void delete(LoyaltyCard loyaltyCard);

    List<LoyaltyCard> findAll();

    LoyaltyCard findByEnterpriseAndClient(Enterprise enterprise, Client client);


}
