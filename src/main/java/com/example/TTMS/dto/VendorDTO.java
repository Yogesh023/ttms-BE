package com.example.TTMS.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {

    private String vendorId;
    private String vendorName;
    private String city;
    private List<String> locations;
    
}
