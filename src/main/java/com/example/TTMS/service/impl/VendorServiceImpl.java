package com.example.TTMS.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.VendorDTO;
import com.example.TTMS.entity.City;
import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.Vendor;
import com.example.TTMS.repository.CityRepo;
import com.example.TTMS.repository.LocationRepo;
import com.example.TTMS.repository.VendorRepo;
import com.example.TTMS.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepo vendorRepo;
    private final CityRepo cityRepo;
    private final LocationRepo locationRepo;

    public VendorServiceImpl(VendorRepo vendorRepo, CityRepo cityRepo, LocationRepo locationRepo) {
        this.vendorRepo = vendorRepo;
        this.cityRepo = cityRepo;
        this.locationRepo = locationRepo;
    }

    @Override
    public Vendor addVendor(VendorDTO vendorDto) {

        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorDto.getVendorId());
        vendor.setVendorName(vendorDto.getVendorName());
        City city = cityRepo.findById(vendorDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        vendor.setCity(city);
        List<Location> locations = new ArrayList<>();
        for (String locationId : vendorDto.getLocations()) {
            Location location = locationRepo.findById(locationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
            locations.add(location);
        }
        vendor.setLocations(locations);
        return vendorRepo.save(vendor);
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepo.findAll();
    }

    @Override
    public Vendor getVendorById(String vendorId) {

        return vendorRepo.findById(vendorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found"));
    }

    @Override
    public Vendor updateVendor(String id, VendorDTO vendorDto) {
        Vendor existing = vendorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found"));
        City city = cityRepo.findById(vendorDto.getCity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        existing.setVendorId(vendorDto.getVendorId());
        existing.setVendorName(vendorDto.getVendorName());
        existing.setCity(city);
        List<Location> locations = new ArrayList<>();
        for (String locationId : vendorDto.getLocations()) {
            Location location = locationRepo.findById(locationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
            locations.add(location);
        }
        existing.setLocations(locations);

        return vendorRepo.save(existing);
    }

    @Override
    public void deleteVendor(String id) {
        if (!vendorRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found");
        }
        vendorRepo.deleteById(id);
    }

}
