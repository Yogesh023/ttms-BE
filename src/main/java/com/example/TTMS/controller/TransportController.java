package com.example.TTMS.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TTMS.dto.ApiResponse;
import com.example.TTMS.dto.TransportDto;
import com.example.TTMS.entity.Transport;
import com.example.TTMS.service.TransportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transport")
public class TransportController {

    private final TransportService transportService;

    public TransportController(TransportService transportService){
        this.transportService = transportService;
    }

    @PostMapping()
    public ApiResponse<Transport> addTransport(@Valid @RequestBody TransportDto transport){
        return ApiResponse.success("Transport added successfully",transportService.addTransport(transport));
    }

    @GetMapping()
    public ApiResponse<List<Transport>> getAllTransports(){
        return ApiResponse.success(transportService.getAllTransports());
    }
}
