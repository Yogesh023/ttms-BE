package com.example.TTMS.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.LocationCost;

public interface LocationCostRepo extends MongoRepository<LocationCost, String> {
    
}
