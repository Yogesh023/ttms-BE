package com.example.TTMS.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.LocationCostDto;
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

    public LocationCostServiceImpl(LocationCostRepo locationCostRepo, LocationRepo locationRepo, CityRepo cityRepo) {
        this.locationCostRepo = locationCostRepo;
        this.locationRepo = locationRepo;
        this.cityRepo = cityRepo;
    }

    @Override
    public LocationCost addLocationCost(LocationCostDto locationCostDto) {

        LocationCost locationCost = new LocationCost();
        locationCost.setPickupLocation(locationRepo.findById(locationCostDto.getPickupLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found")));
        locationCost.setDropLocation(locationRepo.findById(locationCostDto.getDropLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drop location not found")));
        locationCost.setCity(cityRepo.findById(locationCostDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found")));
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

        existing.setPickupLocation(locationRepo.findById(locationCostDto.getPickupLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup location not found")));
        existing.setDropLocation(locationRepo.findById(locationCostDto.getDropLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drop location not found")));
        existing.setCity(cityRepo.findById(locationCostDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found")));
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
