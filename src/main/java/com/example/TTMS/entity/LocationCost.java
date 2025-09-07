package com.example.TTMS.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCost {

    @Id
    private String id;
    private String pickupLocation;
    private String dropLocation;
    
}
