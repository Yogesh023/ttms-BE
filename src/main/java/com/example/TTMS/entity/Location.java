package com.example.TTMS.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    private String id;
    private String city;
    private String locationId;
    private String locationName;

}
