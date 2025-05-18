package com.eon.springCaching.service;

import com.eon.springCaching.model.PhoneNumber;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.eon.springCaching.repository.PhoneCatalogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneCatalogService {

    private final PhoneCatalogRepository repo;

    public PhoneCatalogService(PhoneCatalogRepository repo) {
        this.repo = repo;
    }
    @Cacheable(value = "phoneNumbersByName", key = "#name")
    public List<PhoneNumber> findByName(String name) {
        System.out.println("DB HIT for name: " + name);
        return repo.findByName(name);
    }

    public Optional<PhoneNumber> findById(String id) {
        return repo.findById(id);
    }

    public PhoneNumber create(PhoneNumber phoneNumber) {
        return repo.save(phoneNumber);
    }

    public PhoneNumber updateById(String id, PhoneNumber newPhoneNumber) {
        Optional<PhoneNumber> optionalPhone = repo.findById(id);
        if (optionalPhone.isPresent()) {
            PhoneNumber existingPhone = optionalPhone.get();
            existingPhone.setName(newPhoneNumber.getName());
            existingPhone.setNumber(newPhoneNumber.getNumber());
            return repo.save(existingPhone);  // Save updates existing doc, _id stays the same
        } else {
            return null;  // Or throw an exception, or handle "not found"
        }
    }

    @CacheEvict(value = "phoneNumbersByName", key = "#name")
    public void deleteById(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("PhoneNumber with id " + id + " not found.");
        }
    }

    public List<PhoneNumber> findByNumber(String number) {
        return repo.findByNumber(number);
    }

    public List<PhoneNumber> findAll() {
        return repo.findAll();
    }
}