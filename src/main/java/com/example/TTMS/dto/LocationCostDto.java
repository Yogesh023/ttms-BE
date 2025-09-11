package com.example.TTMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCostDto {
    
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Pickup Location is required")
    private String pickupLocation;
    @NotBlank(message = "Drop Location is required")
    private String dropLocation;
    @NotNull(message = "Cost is required")
    private double cost;
}
