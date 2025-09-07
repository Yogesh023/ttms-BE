package com.example.TTMS.service;

import java.util.List;

import com.example.TTMS.dto.VendorDTO;
import com.example.TTMS.entity.Vendor;

public interface VendorService {

    Vendor addVendor(VendorDTO vendorDto);

    List<Vendor> getAllVendors();

    Vendor getVendorById(String vendorId);
    
}
