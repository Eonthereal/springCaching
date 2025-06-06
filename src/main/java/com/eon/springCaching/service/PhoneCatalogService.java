package com.eon.springCaching.service;

import com.eon.springCaching.model.PhoneNumber;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.eon.springCaching.repository.PhoneCatalogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneCatalogService {

    private final PhoneCatalogRepository repo;
    private final CacheManager cacheManager;

    public PhoneCatalogService(PhoneCatalogRepository repo, CacheManager cacheManager) {
        this.repo = repo;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "phoneById", key = "#id")
    public Optional<PhoneNumber> findById(String id) {
        return repo.findById(id);
    }

    @Cacheable(value = "phoneByName", key = "#name")
    public List<PhoneNumber> findByName(String name) {
        System.out.println("DB HIT for name: " + name);
        return repo.findByName(name);
    }

    @CacheEvict(value = {"phoneById", "phoneByName"}, allEntries = true) // fallback option
    public PhoneNumber updateById(String id, PhoneNumber newPhoneNumber) {
        Optional<PhoneNumber> optionalPhone = repo.findById(id);
        if (optionalPhone.isPresent()) {
            PhoneNumber existingPhone = optionalPhone.get();

            // Evict using the correct key values before change
            evictCacheFor(existingPhone.getId(), existingPhone.getName());

            existingPhone.setName(newPhoneNumber.getName());
            existingPhone.setNumber(newPhoneNumber.getNumber());
            return repo.save(existingPhone);
        } else {
            return null;
        }
    }

    @CacheEvict(value = {"phoneById", "phoneByName"}, allEntries = true) // fallback option
    public void deleteById(String id) {
        PhoneNumber phone = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("PhoneNumber with id " + id + " not found."));

        evictCacheFor(id, phone.getName());
        repo.deleteById(id);
    }

    public List<PhoneNumber> findByNumber(String number) {
        return repo.findByNumber(number);
    }

    public List<PhoneNumber> findAll() {
        return repo.findAll();
    }

    public PhoneNumber create(PhoneNumber phoneNumber) {
        return repo.save(phoneNumber);
    }

    private void evictCacheFor(String id, String name) {
        Cache idCache = cacheManager.getCache("phoneById");
        Cache nameCache = cacheManager.getCache("phoneByName");

        if (idCache != null) {
            idCache.evict(id);
        }

        if (nameCache != null) {
            nameCache.evict(name);
        }
    }



}