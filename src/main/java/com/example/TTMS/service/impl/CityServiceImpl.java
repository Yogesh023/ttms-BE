package com.example.TTMS.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.entity.City;
import com.example.TTMS.repository.CityRepo;
import com.example.TTMS.service.CityService;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepo cityRepo;

    public CityServiceImpl(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    @Override
    public City addCity(City city) {
        return cityRepo.save(city);
    }

    @Override
    public List<City> getAllCity() {
        return cityRepo.findAll();
    }

    @Override
    public City getCityById(String id) {
        return cityRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
    }

}
