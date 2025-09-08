package com.example.TTMS.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.VendorDTO;
import com.example.TTMS.entity.Vendor;
import com.example.TTMS.repository.VendorRepo;
import com.example.TTMS.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepo vendorRepo;

    public VendorServiceImpl(VendorRepo vendorRepo) {
        this.vendorRepo = vendorRepo;
    }

    @Override
    public Vendor addVendor(VendorDTO vendorDto) {

        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorDto.getVendorId());
        vendor.setVendorName(vendorDto.getVendorName());
        vendor.setCity(vendorDto.getCity());
        vendor.setLocations(vendorDto.getLocations());
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

        existing.setVendorId(vendorDto.getVendorId());
        existing.setVendorName(vendorDto.getVendorName());
        existing.setCity(vendorDto.getCity());
        existing.setLocations(vendorDto.getLocations());

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
