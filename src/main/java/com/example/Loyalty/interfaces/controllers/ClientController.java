package com.example.Loyalty.interfaces.controllers;

import com.example.Loyalty.domain.entities.Admin;
import com.example.Loyalty.domain.entities.Client;
import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.domain.entities.LoyaltyCard;
import com.example.Loyalty.infrastructure.gmail.GmailService;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import com.example.Loyalty.payloads.ClientControllerCreateRequest;
import com.example.Loyalty.usecases.AdminUseCase;
import com.example.Loyalty.usecases.ClientUseCase;
import com.example.Loyalty.usecases.EnterpriseUseCase;
import com.example.Loyalty.usecases.LoyaltyUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientUseCase clientUseCase;
    private final LoyaltyUseCase loyaltyUseCase;
    private final EnterpriseUseCase enterpriseUseCase;
    private final GmailService gmailService;

    public ClientController(ClientUseCase clientUseCase, LoyaltyUseCase loyaltyUseCase, EnterpriseUseCase enterpriseUseCase, GmailService gmailService) {
        this.clientUseCase = clientUseCase;
        this.loyaltyUseCase = loyaltyUseCase;
        this.enterpriseUseCase = enterpriseUseCase;
        this.gmailService = gmailService;
    }

    @PostMapping("/create")
    public Client create(@RequestBody ClientControllerCreateRequest newCLient) {
        System.out.println("Create client = " + newCLient);
        try {
            Client clientExist = clientUseCase.isClientExist(newCLient.getClient().getEmail());
            Enterprise enterpriseExist = enterpriseUseCase.isEnterpriseExist(newCLient.getEnterprise().getId());

            if (clientExist != null && enterpriseExist != null) {
                System.out.println("#### LE CLIENT EXIST ####");

                LoyaltyCard loyaltyCardExist = loyaltyUseCase.isLoyaltyExist(enterpriseExist, clientExist);

                if (loyaltyCardExist != null) {
                    // TODO : mise à jour de la carte de fidélité
                    // send mail

                    System.out.println("La carte de fidélité existe déjà.");
                } else {
                    // create loyalty with managed entities
                    loyaltyUseCase.createLoyaltyCard(enterpriseExist, clientExist);

                    gmailService.sendEmail(clientExist.getEmail(), "Carte de fidélité", "Votre carte de fidélité");

//                    gmailService.sendEmail(clientExist.getEmail(), "Carte de fidélité", "Votre carte de fidélité");
                }

                return clientExist; // Ne pas créer le client à nouveau
            }

            System.out.println("#### LE CLIENT N'EXISTE PAS ####");
            // Le client n'existe pas, on le crée
            Client savedClient = clientUseCase.createClient(newCLient.getClient());
            System.out.println("#### LE CLIENT EST CREE ####");
            // Puis on crée la carte de fidélité
            System.out.println("#### LA CARTE DE FIDELITE EST CREE ####");
            loyaltyUseCase.createLoyaltyCard(enterpriseExist, savedClient);

            gmailService.sendEmail(savedClient.getEmail(), "Carte de fidélité", "Votre carte de fidélité");
            System.out.println("#### LA CARTE DE FIDELITE EST CREE ####");

            return savedClient;
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

}
