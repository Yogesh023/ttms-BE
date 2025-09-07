package com.example.TTMS.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.City;

public interface CityRepo extends MongoRepository<City, String> {
    
}
