package com.example.TTMS.service;

import java.util.List;

import com.example.TTMS.entity.User;

public interface UserService {

    User addUser(User user);

    List<User> getAllUser();

    User getUserById(String id);
    
}
