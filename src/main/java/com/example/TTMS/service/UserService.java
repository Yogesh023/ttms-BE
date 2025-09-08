package com.example.TTMS.service;

import java.util.List;

import com.example.TTMS.dto.Login;
import com.example.TTMS.entity.User;

public interface UserService {

    User addUser(User user);

    List<User> getAllUser();

    User getUserById(String id);

    User validateLoginCredentials(Login login);

    User updateUser(String id, User user);

    void deleteUser(String id);

}
