package com.example.Loyalty.infrastructure.database;

import com.example.Loyalty.domain.entities.Enterprise;
import com.example.Loyalty.domain.repositories.EnterpriseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoEnterpriseRepository extends MongoRepository<Enterprise, String>, EnterpriseRepository {

}
