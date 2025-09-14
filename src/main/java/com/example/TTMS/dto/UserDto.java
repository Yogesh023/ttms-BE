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
    @NotEmpty(message = "Locations is required")
    private List<String> locations;
    @NotBlank(message = "Mobile No is required")
    private String mobileNo;
    @NotBlank(message = "City ID is required")
    private String cityId;
    private String email;
    // @NotBlank(message = "Password is required")
    // private String password;
    @NotBlank(message = "Role is required")
    private String role;
    
}
