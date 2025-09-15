package com.example.TTMS.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.example.TTMS.dto.RideTicketDto;
import com.example.TTMS.entity.RideTicket;

public interface RideTicketService {

    RideTicket createRideTicket(RideTicketDto rideTicket);

    List<RideTicket> getMyTickets(String search, Authentication authentication);

}
