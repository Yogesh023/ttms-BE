package com.example.TTMS.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.TTMS.dto.DashboardCount;
import com.example.TTMS.entity.City;
import com.example.TTMS.entity.RideTicket;
import com.example.TTMS.repository.CityRepo;
import com.example.TTMS.repository.LocationRepo;
import com.example.TTMS.repository.RideTicketRepo;
import com.example.TTMS.repository.TransportRepo;
import com.example.TTMS.repository.UserRepo;
import com.example.TTMS.repository.VendorRepo;
import com.example.TTMS.service.DashboardService;
import org.springframework.data.mongodb.core.MongoTemplate;

@Service
public class DashboardServiceImpl implements DashboardService{

    private final CityRepo cityRepo;
    private final LocationRepo locationRepo;
    private final UserRepo userRepo;
    private final VendorRepo vendorRepo;
    private final TransportRepo transportRepo;
    private final RideTicketRepo rideTicketRepo;
    private final MongoTemplate mongoTemplate;

    public DashboardServiceImpl(CityRepo cityRepo, LocationRepo locationRepo, UserRepo userRepo,
            VendorRepo vendorRepo, TransportRepo transportRepo, RideTicketRepo rideTicketRepo,
            MongoTemplate mongoTemplate) {
        this.cityRepo = cityRepo;
        this.locationRepo = locationRepo;
        this.userRepo = userRepo;
        this.vendorRepo = vendorRepo;
        this.transportRepo = transportRepo;
        this.rideTicketRepo = rideTicketRepo;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public DashboardCount getDashboardCounts(String cityId, String locationId, String vendorId, String status,
            LocalDate startDate, LocalDate endDate) {

        long cityCount = 0;
        long locationCount = 0;
        long userCount = 0;
        long vendorCount = 0;
        long transportCount = 0;
        long ticketCount = 0;
        Map<String, Double> cityCostMap = new HashMap<>();

        Query ticketQuery = new Query();

        // 🔹 1️⃣ City filter
        if (cityId != null && !cityId.isEmpty()) {
            City city = cityRepo.findById(cityId).orElse(null);
            if (city != null) {
                cityCount = 1;
                locationCount = locationRepo.countByCity(city);
                userCount = userRepo.countByCity(city);
                vendorCount = vendorRepo.countByCity(city);

                ticketQuery.addCriteria(Criteria.where("City._id").is(cityId));
            }
        } else {
            cityCount = cityRepo.count();
            locationCount = locationRepo.count();
            userCount = userRepo.count();
            vendorCount = vendorRepo.count();
            transportCount = transportRepo.count();
        }

        // 🔹 2️⃣ Location filter (if cityId is empty)
        if ((cityId == null || cityId.isEmpty()) && locationId != null && !locationId.isEmpty()) {
            ticketQuery.addCriteria(Criteria.where("pickupLocation._id").is(locationId));
        }

        // 🔹 3️⃣ Status filter
        if (status != null && !status.isEmpty()) {
            ticketQuery.addCriteria(Criteria.where("status").is(status));
        }

        // 🔹 4️⃣ Date range filter
        if (startDate != null && endDate != null) {
            ticketQuery.addCriteria(Criteria.where("pickupDate").gte(startDate).lte(endDate));
        }

        // 🔹 5️⃣ Ticket count
        ticketCount = mongoTemplate.count(ticketQuery, RideTicket.class);

        // 🔹 6️⃣ Cost aggregation via repo
        cityCostMap = rideTicketRepo.getCityCostSummary(cityId, locationId, status, startDate, endDate, mongoTemplate);

        return new DashboardCount(
                cityCount,
                locationCount,
                userCount,
                vendorCount,
                transportCount,
                ticketCount,
                cityCostMap
        );
    }

}
