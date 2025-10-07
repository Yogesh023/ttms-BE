package com.example.TTMS.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.RideTicket;

public interface RideTicketRepo extends MongoRepository<RideTicket, String> {

    List<RideTicket> findByPickupDate(LocalDate today);

}
