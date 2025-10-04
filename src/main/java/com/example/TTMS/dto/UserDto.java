package com.example.TTMS.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "User ID is required")
    private String userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Address is required")
    private String address;
    @NotEmpty(message = "Locations is required")
    private List<String> locations;
    @NotBlank(message = "Mobile No is required")
    private String mobileNo;
    @NotBlank(message = "Pickup Location is required")
    private String pickupLocation;
    @NotBlank(message = "Transport is required")
    private String transport;
    @NotBlank(message = "No of Person is required")
    private int noOfPerson;
    private String email;
    // @NotBlank(message = "Password is required")
    // private String password;
    @NotBlank(message = "Role is required")
    private String role;
    
}
