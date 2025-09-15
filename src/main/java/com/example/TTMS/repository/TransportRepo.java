package com.example.TTMS.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.Transport;

public interface TransportRepo extends MongoRepository<Transport, String> {

    boolean existsByLocationsContains(Location location);

    Optional<Transport> findByTransportId(String transportId);

}
