package com.example.TTMS.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.TransportDto;
import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.Transport;
import com.example.TTMS.entity.Vendor;
import com.example.TTMS.repository.LocationRepo;
import com.example.TTMS.repository.TransportRepo;
import com.example.TTMS.repository.VendorRepo;
import com.example.TTMS.service.TransportService;

import jakarta.validation.Valid;

@Service
public class TransportServiceImpl implements TransportService {

    private final TransportRepo transportRepo;
    private final VendorRepo vendorRepo;
    private final LocationRepo locationRepo;

    public TransportServiceImpl(TransportRepo transportRepo, VendorRepo vendorRepo, LocationRepo locationRepo) {
        this.transportRepo = transportRepo;
        this.vendorRepo = vendorRepo;
        this.locationRepo = locationRepo;
    }

    @Override
    public Transport addTransport(@Valid TransportDto transportDto) {

        Transport transport = new Transport();
        transport.setVehicleNo(transportDto.getVehicleNo());
        transport.setOwnerDetails(transportDto.getOwnerDetails());
        transport.setContact(transportDto.getContact());
        transport.setType(transportDto.getType());
        transport.setSeater(transportDto.getSeater());
        Vendor vendor = vendorRepo.findById(transportDto.getVendor())
                .orElseThrow(() -> new RuntimeException("vendor not found"));
        transport.setVendor(vendor);
        List<Location> locations = new ArrayList<>();
        for (String locationId : transportDto.getLocations()) {
            Location location = locationRepo.findById(locationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
            locations.add(location);
        }
        transport.setLocations(locations);
        return transportRepo.save(transport);
    }

    @Override
    public List<Transport> getAllTransports() {
        return transportRepo.findAll();
    }

}
