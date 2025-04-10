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
    private final AdminRepository adminRepository;


    @Autowired
    public LoyaltyUseCase(LoyaltyRepository loyaltyRepository, SecurityConfig securityConfig, ClientRepository clientRepository, AdminRepository adminRepository) {
        this.loyaltyRepository = loyaltyRepository;
        this.securityConfig = securityConfig;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
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

    public LoyaltyCard updateLoyaltyCard(String AdminEmail, Long idClient) {
        try {


            Admin admin = adminRepository.findByEmail(AdminEmail)
                    .orElseThrow(() -> new EnterpriseException("Admin not found"));
            System.out.println("##### ADMIN #####");
            System.out.println("admin = " + admin.getEmail());

            Client client = clientRepository.findById(idClient)
                    .orElseThrow(() -> new EnterpriseException("Client not found"));
            System.out.println("##### CLIENT #####");
            System.out.println("client = " + client.getEmail());

            // Check if the client is already associated with a loyalty card

            LoyaltyCard card = loyaltyRepository.findByEnterpriseAndClient(admin.getEnterprise(), client);

            if (card == null) {
                throw new EnterpriseException("Loyalty card not found");

            }

            int currentPoints = card.getPoints();

            card.setPoints(currentPoints + 100);

            return loyaltyRepository.save(card);

//            return loyaltyRepository.save(new LoyaltyCard(adminRepo.getEnterprise(), clientRepo));
        } catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

}
