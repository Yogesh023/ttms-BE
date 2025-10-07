package com.example.TTMS.service.impl;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TTMS.config.JwtHelper;
import com.example.TTMS.dto.Login;
import com.example.TTMS.dto.UserDto;
import com.example.TTMS.entity.City;
import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.Transport;
import com.example.TTMS.entity.User;
import com.example.TTMS.repository.CityRepo;
import com.example.TTMS.repository.LocationRepo;
import com.example.TTMS.repository.TransportRepo;
import com.example.TTMS.repository.UserRepo;
import com.example.TTMS.service.RideTicketService;
import com.example.TTMS.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final LocationRepo locationRepo;
    // private final JwtHelper jwtHelper;
    private final TransportRepo transportRepo;
    private final CityRepo cityRepo;
    private final RideTicketService rideTicketService;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder,
            LocationRepo locationRepo, JwtHelper jwtHelper, TransportRepo transportRepo, CityRepo cityRepo,
            RideTicketService rideTicketService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.locationRepo = locationRepo;
        // this.jwtHelper = jwtHelper;
        this.transportRepo = transportRepo;
        this.cityRepo = cityRepo;
        this.rideTicketService = rideTicketService;
    }

    @Override
    public User addUser(UserDto userDto) {

        User user = new User();
        boolean userIdExists = userRepo.findByUserId(userDto.getUserId()).isPresent();
        if (userIdExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserId already exists");
        }

        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setAddress(userDto.getAddress());
        user.setMobileNo(userDto.getMobileNo());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        City city = cityRepo.findById(userDto.getCityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        user.setCity(city);
        user.setPassword(passwordEncoder.encode("12345678"));
        Location location = locationRepo.findById(userDto.getPickupLocation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
        user.setPickupLocation(location);
        Transport transport = transportRepo.findById(userDto.getTransport())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transport not found"));
        user.setNoOfPerson(userDto.getNoOfPerson());
        user.setTransport(transport);
        user.setNoOfPerson(userDto.getNoOfPerson());
        user = userRepo.save(user);
        rideTicketService.createRide(user);
        return user;
    }

    @Override
    public List<User> getAllUser(Authentication authentication) {

        // Map<String, Object> userDetails = jwtHelper.getUserDetails();
        // String role = (String) userDetails.get("role");
        return userRepo.findByRole("User");
    }

    @Override
    public User getUserById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

    @Override
    public User validateLoginCredentials(Login login) {
        return userRepo.findByUserId(login.getUserId())
                .filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword()))
                .orElse(null);
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
        if (userDto.getAddress() != null && !userDto.getAddress().isBlank()) {
            existing.setAddress(userDto.getAddress());
        }
        if (userDto.getCityId() != null && !userDto.getCityId().isBlank()) {
            City city = cityRepo.findById(userDto.getCityId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
            existing.setCity(city);
        }
        if (userDto.getPickupLocation() != null && !userDto.getPickupLocation().isEmpty()) {
            Location location = locationRepo.findById(userDto.getPickupLocation())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
            existing.setPickupLocation(location);
        }
        if (userDto.getMobileNo() != null && !userDto.getMobileNo().isBlank()) {
            existing.setMobileNo(userDto.getMobileNo());
        }
        if (userDto.getTransport() != null) {
            Transport transport = transportRepo.findById(userDto.getTransport())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
            existing.setTransport(transport);
        }
        if (userDto.getNoOfPerson() != 0) {
            existing.setNoOfPerson(userDto.getNoOfPerson());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            existing.setEmail(userDto.getEmail());
        }
        if (userDto.getRole() != null && !userDto.getRole().isBlank()) {
            existing.setRole(userDto.getRole());
        }
        existing.setPassword(passwordEncoder.encode("12345678"));
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
