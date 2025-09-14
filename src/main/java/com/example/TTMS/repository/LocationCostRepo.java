package com.example.TTMS.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.City;
import com.example.TTMS.entity.Location;
import com.example.TTMS.entity.LocationCost;

public interface LocationCostRepo extends MongoRepository<LocationCost, String> {

    default LocationCost findByPickUpAndDropLocation(String pickupLocation, String dropLocation, MongoTemplate mongoTemplate){

        Query query = new Query();
        query.addCriteria(Criteria.where("pickupLocation.id").is(pickupLocation)
                .and("dropLocation.id").is(dropLocation));
        return mongoTemplate.findOne(query, LocationCost.class);
    }

    default LocationCost findByCityAndPickupLocationAndDropLocation(City city, Location pickupLocation, Location dropLocation, MongoTemplate mongoTemplate){

        Query query = new Query();
        query.addCriteria(Criteria.where("city.id").is(city.getId())
                .and("pickupLocation.id").is(pickupLocation.getId())
                .and("dropLocation.id").is(dropLocation.getId()));
        return mongoTemplate.findOne(query, LocationCost.class);
    }

    boolean existsByPickupLocation(Location location);

    boolean existsByDropLocation(Location location);

    boolean existsByCity(City city);
    
}
