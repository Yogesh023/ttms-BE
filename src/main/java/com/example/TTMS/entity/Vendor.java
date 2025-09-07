package com.example.TTMS.entity;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Vendor {

    @Id
    private String id;
    private String vendorId;
    private String vendorName;
    private String city;
    private List<String> locations;
    
}
