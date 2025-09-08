package com.example.TTMS.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "city")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    private String id;
    private String cityId;
    private String cityName;
}
