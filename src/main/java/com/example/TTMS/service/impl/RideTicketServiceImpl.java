package com.example.TTMS.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.config.JwtHelper;
import com.example.TTMS.dto.RideTicketDto;
import com.example.TTMS.entity.City;
import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.LocationCost;
import com.example.TTMS.entity.RideTicket;
import com.example.TTMS.entity.Status;
import com.example.TTMS.entity.Transport;
import com.example.TTMS.entity.TransportStatus;
import com.example.TTMS.entity.User;
import com.example.TTMS.repository.CityRepo;
import com.example.TTMS.repository.LocationCostRepo;
import com.example.TTMS.repository.LocationRepo;
import com.example.TTMS.repository.RideTicketRepo;
import com.example.TTMS.repository.TransportRepo;
import com.example.TTMS.repository.VendorRepo;
import com.example.TTMS.service.RideTicketService;

@Service
public class RideTicketServiceImpl implements RideTicketService {

    private final RideTicketRepo rideTicketRepo;
    private final TransportRepo transportRepo;
    private final CityRepo cityRepo;
    private final LocationRepo locationRepo;
    private final LocationCostRepo locationCostRepo;
    private final MongoTemplate mongoTemplate;
    private final JwtHelper jwtHelper;

    public RideTicketServiceImpl(RideTicketRepo rideTicketRepo, TransportRepo transportRepo, CityRepo cityRepo,
            LocationRepo locationRepo, LocationCostRepo locationCostRepo, MongoTemplate mongoTemplate
            JwtHelper jwtHelper) {
        this.rideTicketRepo = rideTicketRepo;
        this.transportRepo = transportRepo;
        this.cityRepo = cityRepo;
        this.locationRepo = locationRepo;
        this.locationCostRepo = locationCostRepo;
        this.mongoTemplate = mongoTemplate;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public RideTicket createRideTicket(RideTicketDto rideTicketDto) {

        Map<String, Object> userDetails = jwtHelper.getUserDetails();
        String id = (String) userDetails.get("_id");
        String userId = (String) userDetails.get("userId");

        LocationCost cost = locationCostRepo.findByPickUpAndDropLocation(rideTicketDto.getPickupLocation(),
                rideTicketDto.getDropLocation(), mongoTemplate);
        if (cost == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pick up and Drop location not found");
        }
        Location pickupLocation = locationRepo.findById(rideTicketDto.getPickupLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found"));
        Location dropLocation = locationRepo.findById(rideTicketDto.getDropLocation())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drop location not found"));
        Transport transport = transportRepo.findById(rideTicketDto.getTransport())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transport not found"));
        if(!transport.getStatus().equals(TransportStatus.AVAILABLE.getLabel())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transport not available");
        }
        City city = cityRepo.findById(rideTicketDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "city not found"));
        
        RideTicket rideTicket = new RideTicket();
        rideTicket.setUserId(userId);
        rideTicket.setLocationCost(cost);
        rideTicket.setTransport(transport);
        rideTicket.setCity(city);
        rideTicket.setPickupLocation(pickupLocation);
        rideTicket.setDropLocation(dropLocation);
        rideTicket.setStatus(Status.PENDING.getLabel());
        rideTicket.setCreatedBy(id);
        rideTicket.setCreatedAt(LocalDateTime.now());
        rideTicket.setUpdatedBy(null);
        rideTicket.setUpdatedAt(null);
        transportRepo.updateTransportStatus(rideTicketDto.getTransport(), TransportStatus.ASSIGNED.getLabel(), mongoTemplate);

        return rideTicketRepo.save(rideTicket);

    }

    @Override
    public List<RideTicket> getMyTickets(String search, Authentication authentication) {

        Map<String, Object> userDetails = jwtHelper.getUserDetails();
        String id = (String) userDetails.get("_id");

        String role = (String) userDetails.get("role");

        Query query = new Query();

        switch (role.toUpperCase()) {
            case "VENDOR":
                query.addCriteria(Criteria.where("vendor.id").is(id));
                break;
            case "TRANSPORT":
                query.addCriteria(Criteria.where("transport.id").is(id));
                break;
            case "USER":
                query.addCriteria(Criteria.where("userId").is(id));
                break;
            case "SUPERADMIN":
                break;
            default:
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid role");
        }

        if (search != null && !search.isBlank()) {

            Criteria searchCriteria = new Criteria().orOperator(
                    Criteria.where("status").regex(search, "i"),
                    Criteria.where("vendor.vendorName").regex(search, "i"));
            query.addCriteria(searchCriteria);
        }

        return mongoTemplate.find(query, RideTicket.class);
    }

    @Override
    public void createRide(User user) {

        Map<String, Object> userDetails = jwtHelper.getUserDetails();
        String id = (String) userDetails.get("_id");

        RideTicket rideTicket = new RideTicket();
        rideTicket.setUserId(user.getUserId());
        rideTicket.setTransport(user.getTransport());
        rideTicket.setCity(user.getCity());
        rideTicket.setPickupLocation(user.getPickupLocation());
        rideTicket.setStatus(Status.PENDING.getLabel());
        rideTicket.setCreatedBy(id);
        rideTicket.setCreatedAt(LocalDateTime.now());
        rideTicket.setUpdatedBy(null);
        rideTicket.setUpdatedAt(null);

        rideTicketRepo.save(rideTicket);
        
    }

}
