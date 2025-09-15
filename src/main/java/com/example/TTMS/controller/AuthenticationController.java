package com.example.TTMS.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TTMS.config.JwtHelper;
import com.example.TTMS.dto.JwtResponse;
import com.example.TTMS.dto.Login;
import com.example.TTMS.entity.Transport;
import com.example.TTMS.entity.User;
import com.example.TTMS.entity.Vendor;
import com.example.TTMS.service.TransportService;
import com.example.TTMS.service.UserService;
import com.example.TTMS.service.VendorService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final VendorService vendorService;
    private final TransportService transportService;

    public AuthenticationController(UserService userService, JwtHelper jwtHelper, VendorService vendorService,
            TransportService transportService) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
        this.transportService = transportService;
        this.vendorService = vendorService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody Login login) {

        Map<String, Object> details = new HashMap<>();
        Object account = null;
        String role = null;

        User user = userService.validateLoginCredentials(login);
        if (user != null) {
            account = user;
            role = user.getRole();
            details.put("_id", user.getId());
            details.put("userId", user.getUserId());
            details.put("username", user.getUsername());
            details.put("role", role);
        }

        if (account == null) {
            Vendor vendor = vendorService.validateLoginCredentials(login);
            if (vendor != null) {
                account = vendor;
                role = vendor.getRole();
                details.put("_id", vendor.getId());
                details.put("userId", vendor.getVendorId());
                details.put("username", vendor.getVendorName());
                details.put("role", role);
            }
        }

        if (account == null) {
            Transport transport = transportService.validateLoginCredentials(login);
            if (transport != null) {
                account = transport;
                role = transport.getRole();
                details.put("_id", transport.getId());
                details.put("userId", transport.getTransportId());
                details.put("username", transport.getOwnerDetails());
                details.put("role", role);
            }
        }
        if (account == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        JwtResponse jwtResponse = jwtHelper.createJwtForClaims(login.getUserId(), details);
        Map<String, Object> response = new HashMap<>();
        response.put("account", account);
        response.put("token", jwtResponse.getToken());
        response.put("expiry", jwtResponse.getExpiry());
        response.put("message", "Login successful");

        return ResponseEntity.ok(response);

    }

}
