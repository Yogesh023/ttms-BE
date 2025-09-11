package com.example.TTMS.service.impl;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.dto.Login;
import com.example.TTMS.dto.UserDto;
import com.example.TTMS.entity.City;
import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.User;
import com.example.TTMS.repository.CityRepo;
import com.example.TTMS.repository.LocationRepo;
import com.example.TTMS.repository.UserRepo;
import com.example.TTMS.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CityRepo cityRepo;
    private final LocationRepo locationRepo;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, CityRepo cityRepo, LocationRepo locationRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.cityRepo = cityRepo;
        this.locationRepo = locationRepo;
    }

    @Override
    public User addUser(UserDto userDto) {

        User user = new User();
        boolean userIdExists = userRepo.findByUserId(userDto.getUserId()).isPresent();
        if (userIdExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserId already exists");
        }

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        City city = cityRepo.findById(userDto.getCityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        user.setCity(city);
        List<Location> locations = new ArrayList<>();
        for (String locationId : userDto.getLocations()) {
            Location location = locationRepo.findById(locationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
            locations.add(location);
        }
        user.setLocations(locations);
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
    public User updateUser(String id, UserDto userDto) {
        User existing = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (userDto.getUserId() != null && !userDto.getUserId().isBlank()) {
            existing.setUserId(userDto.getUserId());
        }
        if (userDto.getUsername() != null && !userDto.getUsername().isBlank()) {
            existing.setUsername(userDto.getUsername());
        }
        if (userDto.getLocations() != null && !userDto.getLocations().isEmpty()) {
            List<Location> locations = new ArrayList<>();
            for (String locationId : userDto.getLocations()) {
                Location location = locationRepo.findById(locationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
                locations.add(location);
        }
            existing.setLocations(locations);
        }
        if (userDto.getMobileNo() != null && !userDto.getMobileNo().isBlank()) {
            existing.setMobileNo(userDto.getMobileNo());
        }
        if (userDto.getCityId() != null) {
            City city = cityRepo.findById(userDto.getCityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
            existing.setCity(city);
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            existing.setEmail(userDto.getEmail());
        }
        if (userDto.getRole() != null && !userDto.getRole().isBlank()) {
            existing.setRole(userDto.getRole());
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(userDto.getPassword()));
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
