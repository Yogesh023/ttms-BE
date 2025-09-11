package com.example.TTMS.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TTMS.dto.ApiResponse;
import com.example.TTMS.dto.RideTicketDto;
import com.example.TTMS.entity.RideTicket;
import com.example.TTMS.service.RideTicketService;

@RestController
@RequestMapping("/ride-Ticket")
public class RideTicketController {

    private final RideTicketService rideTicketService;

    public RideTicketController(RideTicketService rideTicketService) {
        this.rideTicketService = rideTicketService;
    }

    @RequestMapping("/create")
    public ApiResponse<RideTicket> createRideTicket(@RequestBody RideTicketDto rideTicketDto) {
        RideTicket rideTicket = rideTicketService.createRideTicket(rideTicketDto);
        return ApiResponse.success("Ride ticket created successfully", rideTicket);
    }


}
