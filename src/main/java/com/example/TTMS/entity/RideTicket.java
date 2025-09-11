package com.example.TTMS.entity;

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
    private City City;
    private Transport transport;
    private LocationCost locationCost;
    private String status;

}
