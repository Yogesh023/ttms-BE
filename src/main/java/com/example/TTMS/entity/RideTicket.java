package com.example.TTMS.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideTicket {

    @Id
    private String id;
    private String userId;
    private City City;
    private Vendor vendor;
    private Transport transport;
    private LocationCost locationCost;
    private Location pickupLocation;
    private Location dropLocation;
    private String status;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    

}
