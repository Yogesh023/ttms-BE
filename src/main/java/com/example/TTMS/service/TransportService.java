package com.example.TTMS.service;

import java.util.List;

import com.example.TTMS.dto.TransportDto;
import com.example.TTMS.entity.Transport;

import jakarta.validation.Valid;

public interface TransportService {

    Transport addTransport(@Valid TransportDto transport);

    List<Transport> getAllTransports();
    
}
