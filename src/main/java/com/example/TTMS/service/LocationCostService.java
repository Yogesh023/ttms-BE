package com.example.TTMS.service;

import java.util.List;

import com.example.TTMS.entity.LocationCost;

public interface LocationCostService {

    LocationCost addLocationCost(LocationCost locationCost);

    List<LocationCost> getAllLocationCost();

    LocationCost getLocationCostById(String id);

    LocationCost updateLocationCost(String id, LocationCost locationCost);

    void deleteLocationCost(String id);

}
