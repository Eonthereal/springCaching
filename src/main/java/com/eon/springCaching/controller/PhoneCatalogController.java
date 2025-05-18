package com.eon.springCaching.controller;

import com.eon.springCaching.model.PhoneNumber;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eon.springCaching.service.PhoneCatalogService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PhoneCatalogController {

    private final PhoneCatalogService phoneCatalogService;

    public PhoneCatalogController(PhoneCatalogService phoneCatalogService){
        this.phoneCatalogService = phoneCatalogService;
    }

    @GetMapping("/numbers/name/{name}")
    public List<PhoneNumber> findByName(@PathVariable String name){
        return phoneCatalogService.findByName(name);
    }

    @GetMapping("/numbers/id/{id}")
    public ResponseEntity<PhoneNumber> findById(@PathVariable String id) {
        Optional<PhoneNumber> phone = phoneCatalogService.findById(id);
        return phone.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/numbers")
    public List<PhoneNumber> findAll() {
        return phoneCatalogService.findAll();
    }

    @PostMapping("/numbers")
    public PhoneNumber create(@RequestBody PhoneNumber phoneNumber) {
        return phoneCatalogService.create(phoneNumber);
    }

    @PutMapping("/numbers/id/{id}")
    public ResponseEntity<PhoneNumber> updateById(@PathVariable String id, @RequestBody PhoneNumber phoneNumber) {
        PhoneNumber updated = phoneCatalogService.updateById(id, phoneNumber);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/numbers/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        try {
            phoneCatalogService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
