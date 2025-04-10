package com.example.Loyalty.usecases;

import com.example.Loyalty.config.SecurityConfig;
import com.example.Loyalty.domain.entities.Admin;
import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.infrastructure.repositories.AdminRepository;
import com.example.Loyalty.infrastructure.repositories.EnterpriseRepository;
import com.example.Loyalty.interfaces.exceptions.EnterpriseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUseCase {

    private final EnterpriseRepository enterpriseRepository;
    private final SecurityConfig securityConfig;
    private final AdminRepository adminRepository;


    @Autowired
    public AdminUseCase(EnterpriseRepository enterpriseRepository, SecurityConfig securityConfig, AdminRepository adminRepository) {
        this.enterpriseRepository = enterpriseRepository;
        this.securityConfig = securityConfig;
        this.adminRepository = adminRepository;
    }

    public Admin createAdmin(Admin newAdmin) {

        try {
            String firstname = newAdmin.getFirstname();
            String lastname = newAdmin.getLastname();
            String email = newAdmin.getEmail();
            String password = newAdmin.getPassword();
            Long enterpriseId = newAdmin.getEnterprise().getId();
            System.out.println("###########################################");
            System.out.println("enterpriseID = " + newAdmin.getEnterprise().getId());
            System.out.println("###########################################");

            if (firstname == null || lastname == null || email == null || password == null || enterpriseId == null) {
                throw new EnterpriseException("All fields are required");
            }

            // Check if the enterprise exists
            Enterprise foundEnterprise = enterpriseRepository.findById(enterpriseId)
                    .orElseThrow(() -> new EnterpriseException("Enterprise not found"));

            // Hash the password
            String hashedPassword = securityConfig.passwordEncoder().encode(password);

            // Set the hashed password
            newAdmin.setPassword(hashedPassword);

            // Set the enterprise ID
            newAdmin.setEnterprise(foundEnterprise);

            // Check if the email already exists
            if (adminRepository.findByEmail(email).isPresent()) {
                throw new EnterpriseException("Email already exists");
            }


            return adminRepository.save(newAdmin);
        }
        catch (Exception e) {
            throw new EnterpriseException("error : " + e.getMessage());
        }
    }

}
