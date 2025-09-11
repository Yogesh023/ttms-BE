package com.example.TTMS.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.TTMS.entity.LocationCost;

public interface LocationCostRepo extends MongoRepository<LocationCost, String> {

    default LocationCost findByPickUpAndDropLocation(String pickupLocation, String dropLocation, MongoTemplate mongoTemplate){

        Query query = new Query();
        query.addCriteria(Criteria.where("pickupLocation.id").is(pickupLocation)
                .and("dropLocation.id").is(dropLocation));
        return mongoTemplate.findOne(query, LocationCost.class);
    }
    
}
