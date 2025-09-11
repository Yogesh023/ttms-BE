package com.example.TTMS.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "transports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transport {

    @Id
    private String id;
    private String vehicleNo;
    private String ownerDetails;
    private String contact;
    private String type;
    private int seater;
    private Vendor vendor;
    private List<Location> locations;

}
