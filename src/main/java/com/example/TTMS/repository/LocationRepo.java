package com.example.TTMS.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.Location;

public interface LocationRepo extends MongoRepository<Location, String> {
    
}
