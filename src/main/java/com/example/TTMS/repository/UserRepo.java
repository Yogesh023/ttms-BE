package com.example.TTMS.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.User;

public interface UserRepo extends MongoRepository<User, String> {
    
}
