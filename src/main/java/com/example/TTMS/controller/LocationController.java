package com.example.TTMS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TTMS.service.LocationService;
import com.example.TTMS.entity.Location;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/location")
@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping()
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {        
        return ResponseEntity.ok(locationService.addLocation(location));
    }

    @GetMapping()
    public ResponseEntity<List<Location>> getAllLocation() {
        return ResponseEntity.ok(locationService.getAllLocation());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") String id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }
    
    
}
