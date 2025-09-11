package com.example.TTMS.service.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.RideTicketDto;
import com.example.TTMS.entity.City;
import com.example.TTMS.entity.LocationCost;
import com.example.TTMS.entity.RideTicket;
import com.example.TTMS.entity.Status;
import com.example.TTMS.entity.Transport;
import com.example.TTMS.repository.CityRepo;
import com.example.TTMS.repository.LocationCostRepo;
import com.example.TTMS.repository.RideTicketRepo;
import com.example.TTMS.repository.TransportRepo;
import com.example.TTMS.service.RideTicketService;

@Service
public class RideTicketServiceImpl implements RideTicketService {

    private final RideTicketRepo rideTicketRepo;
    private final TransportRepo transportRepo;
    private final CityRepo cityRepo;
    private final LocationCostRepo locationCostRepo;
    private final MongoTemplate mongoTemplate;

    public RideTicketServiceImpl(RideTicketRepo rideTicketRepo, TransportRepo transportRepo, CityRepo cityRepo,
            LocationCostRepo locationCostRepo, MongoTemplate mongoTemplate) {
        this.rideTicketRepo = rideTicketRepo;
        this.transportRepo = transportRepo;
        this.cityRepo = cityRepo;
        this.locationCostRepo = locationCostRepo;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public RideTicket createRideTicket(RideTicketDto rideTicketDto) {

        LocationCost cost = locationCostRepo.findByPickUpAndDropLocation(rideTicketDto.getPickupLocation(),
                rideTicketDto.getDropLocation(), mongoTemplate);
        if (cost == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pick up and Drop location not found");
        }
        Transport transport = transportRepo.findById(rideTicketDto.getTransport())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transport not found"));
        City city = cityRepo.findById(rideTicketDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "city not found"));
        RideTicket rideTicket = new RideTicket();
        rideTicket.setLocationCost(cost);
        rideTicket.setTransport(transport);
        rideTicket.setCity(city);
        rideTicket.setStatus(Status.PENDING.getLabel());
        return rideTicketRepo.save(rideTicket);

    }

}
