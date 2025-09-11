package com.example.TTMS.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.Transport;

public interface TransportRepo extends MongoRepository<Transport, String> {

}
