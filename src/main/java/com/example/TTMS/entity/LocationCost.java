package com.example.TTMS.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "locationCost")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCost {

    @Id
    private String id;
    private String pickupLocation;
    private String dropLocation;
    
}
