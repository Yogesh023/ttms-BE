package com.example.TTMS.entity;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String userId;
    private String username;
    @DBRef(lazy = true)
    private List<Location> locations;
    private String mobileNo;
    @DBRef
    private City city;
    private String email;
    private String password;
    private String role;
    
}
