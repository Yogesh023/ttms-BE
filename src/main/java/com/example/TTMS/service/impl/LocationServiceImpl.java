package com.example.TTMS.service.impl;

import java.util.ArrayList;
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
        if (city.getLocations() != null &&
                city.getLocations().stream()
                        .anyMatch(loc -> loc.getLocationName().equalsIgnoreCase(locationDto.getLocationName()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Location with name '" + locationDto.getLocationName() + "' already exists in this city");
        }
        Location location = new Location();
        location.setCity(locationDto.getCity());
        location.setLocationId(locationDto.getLocationId());
        location.setLocationName(locationDto.getLocationName());
        location = locationRepo.save(location);
        if (city.getLocations() == null) {
            city.setLocations(new ArrayList<>());
        }
        city.getLocations().add(location);
        cityRepo.save(city);
        return location;
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
        boolean duplicate = city.getLocations() != null &&
                city.getLocations().stream()
                        .filter(loc -> !loc.getId().equals(existingLocation.getId()))
                        .anyMatch(loc -> loc.getLocationName().equalsIgnoreCase(locationDto.getLocationName()));

        if (duplicate) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Location with name '" + locationDto.getLocationName() + "' already exists in this city");
        }
        existingLocation.setCity(locationDto.getCity());
        existingLocation.setLocationId(locationDto.getLocationId());
        existingLocation.setLocationName(locationDto.getLocationName());

        Location updatedLocation = locationRepo.save(existingLocation);
        List<Location> locations = city.getLocations();
        if (locations == null) {
            locations = new ArrayList<>();
        }
        locations.removeIf(loc -> loc.getId().equals(updatedLocation.getId()));
        locations.add(updatedLocation);
        city.setLocations(locations);
        cityRepo.save(city);
        return updatedLocation;

    }

    @Override
    public void deleteLocation(String id) {
        if (!locationRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }
        locationRepo.deleteById(id);
    }

}
