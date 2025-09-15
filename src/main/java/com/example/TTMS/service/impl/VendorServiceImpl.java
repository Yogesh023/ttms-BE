package com.example.TTMS.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.Login;
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
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;

    public VendorServiceImpl(VendorRepo vendorRepo, CityRepo cityRepo, LocationRepo locationRepo, PasswordEncoder passwordEncoder, MongoTemplate mongoTemplate) {
        this.vendorRepo = vendorRepo;
        this.cityRepo = cityRepo;
        this.locationRepo = locationRepo;
        this.passwordEncoder = passwordEncoder;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Vendor addVendor(VendorDTO vendorDto) {

        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorDto.getVendorId());
        vendor.setVendorName(vendorDto.getVendorName());
        vendor.setAddress(vendorDto.getAddress());
        vendor.setEmail(vendorDto.getEmail());
        vendor.setMobile(vendorDto.getMobile());
        vendor.setPassword(passwordEncoder.encode("12345678"));
        vendor.setRole("Vendor");
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
        existing.setAddress(vendorDto.getAddress());
        existing.setEmail(vendorDto.getEmail());
        existing.setMobile(vendorDto.getMobile());
        existing.setPassword(existing.getPassword());
        existing.setRole("Vendor");
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

    @Override
    public Vendor validateLoginCredentials(Login login) {

        return vendorRepo.findByVendorId(login.getUserId())
                .filter(vendor -> passwordEncoder.matches(login.getPassword(), vendor.getPassword()))
                .orElse(null);
    }

    @Override
    public List<Vendor> getVendorsByCityAndLocation(String cityId, List<String> locationIds) {
        
        List<Vendor> vendors = vendorRepo.getVendorsByCityAndLocation(cityId, locationIds, mongoTemplate);
        if(vendors.isEmpty() || vendors == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transport not found for the locations");
        }
        return vendors;

        // return vendors.stream()
        //           .flatMap(v -> v.getTransport().stream())
        //           .toList();
    }

}
