package com.example.Loyalty.domain.entities;

import jakarta.persistence.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loyalty_cards")
public class LoyaltyCard extends BaseModel {

    @Column(nullable = false)
    private int points = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Enterprise enterprise;

    public LoyaltyCard(Enterprise enterprise, Client client) {
        this.enterprise = enterprise;
        this.client = client;
    }

    // Tu peux aussi ajouter un champ de type "LocalDateTime lastUpdated" si besoin
}
