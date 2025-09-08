package com.example.TTMS.service;

import java.util.List;

import com.example.TTMS.entity.Location;

public interface LocationService {

    Location addLocation(Location location);

    List<Location> getAllLocation();

    Location getLocationById(String id);

    Location updateLocation(String id, Location location);

    void deleteLocation(String id);

}
