package com.example.TTMS.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "City ID is required")
    private String cityId;
    @NotBlank(message = "City Name is required")
    private String cityName;
}
