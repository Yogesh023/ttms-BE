package com.example.TTMS.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "vendor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    @Id
    private String id;
    private String vendorId;
    private String vendorName;
    @DBRef
    private City city;
    @DBRef
    private List<Location> locations;
    
}
