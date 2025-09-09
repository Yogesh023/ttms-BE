package com.example.TTMS.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Pickup Location is required")
    private String pickupLocation;
    @NotBlank(message = "Drop Location is required")
    private String dropLocation;
    
}
