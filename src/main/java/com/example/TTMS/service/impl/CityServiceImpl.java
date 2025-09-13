package com.example.TTMS.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.CityDto;
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
    public City addCity(CityDto cityDto) {

        City city = new City();
        city.setCityId(cityDto.getCityId());
        city.setCityName(cityDto.getCityName());
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

    @Override
    public City updateCity(String id, CityDto cityDto) {
        City existingCity = cityRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));

        existingCity.setCityId(cityDto.getCityId());
        existingCity.setCityName(cityDto.getCityName());

        return cityRepo.save(existingCity);
    }

    @Override
    public void deleteCity(String id) {
        if (!cityRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found");
        }
        cityRepo.deleteById(id);
    }

}
