package com.example.TTMS.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.RideTicket;

public interface RideTicketRepo extends MongoRepository<RideTicket, String> {

  List<RideTicket> getMyyTransportTickets(String id, MongoTemplate mongoTemplate);

}
