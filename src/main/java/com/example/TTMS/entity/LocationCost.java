package com.example.TTMS.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @DBRef
    private City city;
    @DBRef
    private Location pickupLocation;
    @DBRef
    private Location dropLocation;
    private double cost;
    
}
