package com.example.TTMS.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TTMS.entity.City;
import com.example.TTMS.service.CityService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping()
    public ResponseEntity<City> addCity(@RequestBody City city) {        
        return ResponseEntity.ok(cityService.addCity(city));
    }

    @GetMapping()
    public ResponseEntity<List<City>> getAllCity() {
        return ResponseEntity.ok(cityService.getAllCity());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable("id") String id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }
    
}
