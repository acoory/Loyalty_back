package com.example.Loyalty.usecases;


import com.example.Loyalty.config.SecurityConfig;
import com.example.Loyalty.domain.entities.Admin;
import com.example.Loyalty.domain.entities.Client;
import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.domain.entities.LoyaltyCard;
import com.example.Loyalty.infrastructure.repositories.AdminRepository;
import com.example.Loyalty.infrastructure.repositories.ClientRepository;
import com.example.Loyalty.infrastructure.repositories.EnterpriseRepository;
import com.example.Loyalty.infrastructure.repositories.LoyaltyRepository;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyUseCase {

    private final LoyaltyRepository loyaltyRepository;
    private final SecurityConfig securityConfig;
    private final ClientRepository clientRepository;


    @Autowired
    public LoyaltyUseCase(LoyaltyRepository loyaltyRepository, SecurityConfig securityConfig, ClientRepository clientRepository) {
        this.loyaltyRepository = loyaltyRepository;
        this.securityConfig = securityConfig;
        this.clientRepository = clientRepository;
    }

    public LoyaltyCard isLoyaltyExist(Enterprise enterprise, Client client) {
        try {
            System.out.println("##### ENTERPRISE ID #####");
            System.out.println(enterprise.getId());
            return loyaltyRepository.findByEnterpriseAndClient(enterprise, client);
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

    public LoyaltyCard createLoyaltyCard(Enterprise enterprise, Client client) {
        try {

            if (enterprise == null || client == null) {
                throw new EnterpriseException("All fields are required");
            }

            Client clientRepo = clientRepository.findByEmail(client.getEmail())
                    .orElseThrow(() -> new EnterpriseException("Client not found"));
//            new LoyaltyCard(enterprise, client);
            System.out.println("client = " + clientRepo.getEmail());


            return loyaltyRepository.save(new LoyaltyCard(enterprise, clientRepo));
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

}
