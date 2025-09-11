package com.example.TTMS.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.RideTicket;

public interface RideTicketRepo extends MongoRepository<RideTicket, String> {

}
