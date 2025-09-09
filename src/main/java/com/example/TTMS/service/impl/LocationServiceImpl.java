package com.example.TTMS.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.LocationDto;
import com.example.TTMS.entity.City;
import com.example.TTMS.entity.Location;
import com.example.TTMS.repository.CityRepo;
import com.example.TTMS.repository.LocationRepo;
import com.example.TTMS.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepo locationRepo;
    private final CityRepo cityRepo;

    public LocationServiceImpl(LocationRepo locationRepo, CityRepo cityRepo) {
        this.locationRepo = locationRepo;
        this.cityRepo = cityRepo;
    }

    @Override
    public Location addLocation(LocationDto locationDto) {
        City city = cityRepo.findById(locationDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        Location location = new Location();
        location.setCity(city);
        location.setLocationId(locationDto.getLocationId());
        location.setLocationName(locationDto.getLocationName());
        return locationRepo.save(location);
    }

    @Override
    public List<Location> getAllLocation() {
        return locationRepo.findAll();
    }

    @Override
    public Location getLocationById(String id) {
        return locationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
    }

    @Override
    public Location updateLocation(String id, LocationDto locationDto) {
        Location existingLocation = locationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
        City city = cityRepo.findById(locationDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        existingLocation.setCity(city);
        existingLocation.setLocationId(locationDto.getLocationId());
        existingLocation.setLocationName(locationDto.getLocationName());

        return locationRepo.save(existingLocation);
    }

    @Override
    public void deleteLocation(String id) {
        if (!locationRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }
        locationRepo.deleteById(id);
    }

}
