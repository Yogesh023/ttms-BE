package com.example.TTMS.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.Login;
import com.example.TTMS.entity.User;
import com.example.TTMS.repository.UserRepo;
import com.example.TTMS.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

    @Override
    public User validateLoginCredentials(Login login) {
        User user = userRepo.findByUserId(login.getUserId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

    @Override
    public User updateUser(String id, User user) {
        User existing = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getUserId() != null && !user.getUserId().isBlank()) {
            existing.setUserId(user.getUserId());
        }
        if (user.getUsername() != null && !user.getUsername().isBlank()) {
            existing.setUsername(user.getUsername());
        }
        if (user.getLocations() != null && !user.getLocations().isEmpty()) {
            existing.setLocations(user.getLocations());
        }
        if (user.getMobileNo() != null && !user.getMobileNo().isBlank()) {
            existing.setMobileNo(user.getMobileNo());
        }
        if (user.getCity() != null && !user.getCity().isBlank()) {
            existing.setCity(user.getCity());
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            existing.setEmail(user.getEmail());
        }
        if (user.getRole() != null && !user.getRole().isBlank()) {
            existing.setRole(user.getRole());
        }
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepo.save(existing);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepo.deleteById(id);
    }

}
