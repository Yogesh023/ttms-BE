package com.example.TTMS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TTMS.service.LocationCostService;

@RestController
@RequestMapping("/locationcost")
public class LocationCostController {

    private final LocationCostService locationCostService;

    public LocationCostController(LocationCostService locationCostService) {
        this.locationCostService = locationCostService;
    }

    
    
}
