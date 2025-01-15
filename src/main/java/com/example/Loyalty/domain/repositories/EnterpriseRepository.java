package com.example.Loyalty.domain.repositories;
import com.example.Loyalty.domain.entities.Enterprise;
import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository {
    Enterprise save(Enterprise enterprise);
    Optional<Enterprise> findById(String id);
    List<Enterprise> findAll();
    void deleteById(String id);

}