package com.example.TTMS.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportDto {

    @NotBlank(message = "Vehicle Number is required")
    private String vehicleNo;
    @NotBlank(message = "Owner Details is required")
    private String ownerDetails;
    @NotBlank(message = "Contact is required")
    private String contact;
    @NotBlank(message = "Type is required")
    private String type;
    @NotNull(message = "Seater is required")
    private int seater;
    @NotBlank(message = "Vendor is required")
    private String vendor;
    @NotEmpty(message = "At least one location is required")
    private List<String> locations;
    
}
