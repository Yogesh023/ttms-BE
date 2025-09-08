package com.example.TTMS.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.entity.LocationCost;
import com.example.TTMS.repository.LocationCostRepo;
import com.example.TTMS.service.LocationCostService;

@Service
public class LocationCostServiceImpl implements LocationCostService {

    private final LocationCostRepo locationCostRepo;

    public LocationCostServiceImpl(LocationCostRepo locationCostRepo) {
        this.locationCostRepo = locationCostRepo;
    }

    @Override
    public LocationCost addLocationCost(LocationCost locationCost) {
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
    public LocationCost updateLocationCost(String id, LocationCost locationCost) {
        LocationCost existing = locationCostRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location cost not found"));

        existing.setPickupLocation(locationCost.getPickupLocation());
        existing.setDropLocation(locationCost.getDropLocation());

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
