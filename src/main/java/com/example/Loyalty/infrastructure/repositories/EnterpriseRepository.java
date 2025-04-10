package com.example.Loyalty.infrastructure.repositories;


import com.example.Loyalty.domain.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    Enterprise save(Enterprise enterprise);

    Optional<Enterprise> findById(Long id);

    void delete(Enterprise enterprise);

    List<Enterprise> findAll();
}
