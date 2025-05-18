package com.eon.springCaching.repository;

import com.eon.springCaching.model.PhoneNumber;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface PhoneCatalogRepository extends MongoRepository<PhoneNumber, String> {
    List<PhoneNumber> findByName(String name);
    List<PhoneNumber> findByNumber(String number);

}