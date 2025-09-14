package com.example.TTMS.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.TTMS.entity.City;
import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.Vendor;

@Repository
public interface VendorRepo extends MongoRepository<Vendor, String> {

    boolean existsByLocationsContains(Location location);

    boolean existsByCity(City city);
    
}
