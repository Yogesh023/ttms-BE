package com.example.TTMS.service;

import java.util.List;

import com.example.TTMS.dto.Login;
import com.example.TTMS.dto.UserDto;
import com.example.TTMS.entity.User;

public interface UserService {

    User addUser(UserDto userDto);

    List<User> getAllUser();

    User getUserById(String id);

    User validateLoginCredentials(Login login);

    User updateUser(String id, UserDto userDto);

    void deleteUser(String id);

}
