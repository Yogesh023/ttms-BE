package com.example.TTMS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.City;
import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.User;

public interface UserRepo extends MongoRepository<User, String> {

    Optional<User> findByUserId(String userId);

    boolean existsByLocationsContains(Location location);

    boolean existsByCity(City city);

    List<User> findByRole(String role);
    
}
