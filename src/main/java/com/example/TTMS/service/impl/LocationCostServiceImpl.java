package com.example.TTMS.service.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.LocationCostDto;
import com.example.TTMS.entity.City;
import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.LocationCost;
import com.example.TTMS.repository.CityRepo;
import com.example.TTMS.repository.LocationCostRepo;
import com.example.TTMS.repository.LocationRepo;
import com.example.TTMS.service.LocationCostService;

@Service
public class LocationCostServiceImpl implements LocationCostService {

    private final LocationCostRepo locationCostRepo;
    private final LocationRepo locationRepo;
    private final CityRepo cityRepo;
    private final MongoTemplate mongoTemplate;

    public LocationCostServiceImpl(LocationCostRepo locationCostRepo, LocationRepo locationRepo, CityRepo cityRepo,
            MongoTemplate mongoTemplate) {
        this.locationCostRepo = locationCostRepo;
        this.locationRepo = locationRepo;
        this.cityRepo = cityRepo;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public LocationCost addLocationCost(LocationCostDto locationCostDto) {

        Location pickupLocation = locationRepo.findById(locationCostDto.getPickupLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found"));

        Location dropLocation = locationRepo.findById(locationCostDto.getDropLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drop location not found"));

        City city = cityRepo.findById(locationCostDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        LocationCost existing = locationCostRepo
                .findByCityAndPickupLocationAndDropLocation(city, pickupLocation, dropLocation, mongoTemplate);

        if (existing != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Location cost already exists for this combination");
        }

        LocationCost locationCost = new LocationCost();
        locationCost.setPickupLocation(pickupLocation);
        locationCost.setDropLocation(dropLocation);
        locationCost.setCity(city);
        locationCost.setCost(locationCostDto.getCost());
        return locationCostRepo.save(locationCost);
    }

    @Override
    public List<LocationCost> getAllLocationCost() {
        return locationCostRepo.findAll();
    }

    @Override
    public LocationCost getLocationCostById(String id) {
        return locationCostRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location Cost not found"));
    }

    @Override
    public LocationCost updateLocationCost(String id, LocationCostDto locationCostDto) {

        LocationCost existing = locationCostRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location cost not found"));
        Location pickupLocation = locationRepo.findById(locationCostDto.getPickupLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found"));

        Location dropLocation = locationRepo.findById(locationCostDto.getDropLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drop location not found"));

        City city = cityRepo.findById(locationCostDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));

        LocationCost duplicate = locationCostRepo
                .findByCityAndPickupLocationAndDropLocation(city, pickupLocation, dropLocation, mongoTemplate);
        if (duplicate != null && !duplicate.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Location cost already exists for this city, pickup and drop combination");
        }

        existing.setPickupLocation(pickupLocation);
        existing.setDropLocation(dropLocation);
        existing.setCity(city);
        existing.setCost(locationCostDto.getCost());

        return locationCostRepo.save(existing);
    }

    @Override
    public void deleteLocationCost(String id) {
        if (!locationCostRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location cost not found");
        }
        locationCostRepo.deleteById(id);
    }

}
