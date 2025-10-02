package com.example.TTMS.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCostDetails {

    @DBRef
    private Location pickupLocation;
    @DBRef
    private Location dropLocation;
    private double cost;

}
