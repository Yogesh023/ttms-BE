package com.example.TTMS.service;

import com.example.TTMS.dto.RideTicketDto;
import com.example.TTMS.entity.RideTicket;

public interface RideTicketService {

    RideTicket createRideTicket(RideTicketDto rideTicket);

}
