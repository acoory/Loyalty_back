package com.example.Loyalty.infrastructure.repositories;


import com.example.Loyalty.domain.entities.Admin;
import com.example.Loyalty.domain.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin save(Admin admin);

    Optional<Admin> findById(Long id);

    Optional<Admin> findByEmail(String email);

    void delete(Admin admin);

    Optional<Enterprise> getEnterpriseById(Long id);

    List<Admin> findAll();
}
