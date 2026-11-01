package com.auca.lease_track.controller;

import com.auca.lease_track.model.Landlord;
import com.auca.lease_track.service.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/landlords")
public class LandlordController {

    @Autowired
    private LandlordService landlordService;

    @PostMapping("/save")
    public ResponseEntity<String> saveLandlord(@RequestBody Landlord landlord) {
        landlordService.saveLandlord(landlord);
        return new ResponseEntity<>("Landlord saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Landlord>> getAllLandlords() {
        return new ResponseEntity<>(landlordService.getAllLandlords(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLandlordById(@PathVariable UUID id) {
        Optional<Landlord> l = landlordService.getLandlordById(id);
        if (l.isPresent()) {
            return new ResponseEntity<>(l.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The landlord with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLandlord(@PathVariable UUID id, @RequestBody Landlord landlord) {
        Landlord updated = landlordService.updateLandlord(id, landlord);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>("The landlord with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLandlord(@PathVariable UUID id) {
        boolean deleted = landlordService.deleteLandlord(id);
        if (deleted) {
            return new ResponseEntity<>("Landlord deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("The landlord with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/by-property-type")
    public ResponseEntity<List<Landlord>> getLandlordsByPropertyType(@RequestParam String name) {
        List<Landlord> landlords = landlordService.getLandlordsByPropertyType(name);
        return new ResponseEntity<>(landlords, HttpStatus.OK);
    }
    @PutMapping("/{landlordId}/property-types/{propertyTypeId}")
    public ResponseEntity<String> addPropertyTypeToLandlord(@PathVariable UUID landlordId, @PathVariable UUID propertyTypeId) {
        return landlordService.addPropertyTypeToLandlord(landlordId, propertyTypeId);
    }

    @PatchMapping("/{landlordId}/building")
    public ResponseEntity<String> moveLandlordToBuilding(@PathVariable UUID landlordId, @RequestParam int buildingNumber) {
        return landlordService.moveLandlordToBuilding(landlordId, buildingNumber);
    }
}