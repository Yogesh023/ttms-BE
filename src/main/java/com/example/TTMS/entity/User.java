package com.example.TTMS.entity;


import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String userId;
    private String userName;
    private List<String> locations;
    private String mobileNo;
    private String city;
    private String email;
    
}
