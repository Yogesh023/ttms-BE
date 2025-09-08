package com.example.TTMS.service;

import java.util.List;
import com.example.TTMS.entity.City;

public interface CityService{

    City addCity(City city);

    List<City> getAllCity();

    City getCityById(String id);

    City updateCity(String id, City city);

    void deleteCity(String id);
    
}
