package com.example.TTMS.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TTMS.config.JwtHelper;
import com.example.TTMS.dto.Login;
import com.example.TTMS.entity.User;
import com.example.TTMS.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtHelper jwtHelper;

    public AuthenticationController(UserService userService, JwtHelper jwtHelper) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody Login login) {
        try {
            User user = userService.validateLoginCredentials(login);
            Map<String, Object> details = new HashMap<>();
            details.put("_id", user.getId());
            details.put("userId", user.getUserId());
            details.put("username", user.getUsername());
            details.put("role", user.getRole());

            String token = jwtHelper.createJwtForClaims(user.getUsername(), details);
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
