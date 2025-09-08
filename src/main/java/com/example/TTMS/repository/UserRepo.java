package com.example.TTMS.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.User;

public interface UserRepo extends MongoRepository<User, String> {

    Optional<User> findByUserId(String userId);
    
}
