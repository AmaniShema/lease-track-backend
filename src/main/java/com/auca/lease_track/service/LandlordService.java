package com.auca.lease_track.service;

import com.auca.lease_track.model.Building;
import com.auca.lease_track.model.Landlord;
import com.auca.lease_track.model.PropertyType;
import com.auca.lease_track.repository.BuildingRepository;
import com.auca.lease_track.repository.LandlordRepository;
import com.auca.lease_track.repository.PropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LandlordService {

    @Autowired
    private LandlordRepository landlordRepository;

    public Landlord saveLandlord(Landlord landlord) {
        return landlordRepository.save(landlord);
    }

    public List<Landlord> getAllLandlords() {
        return landlordRepository.findAll();
    }

    public Optional<Landlord> getLandlordById(UUID id) {
        return landlordRepository.findById(id);
    }

    public Landlord updateLandlord(UUID id, Landlord updated) {
        Optional<Landlord> existing = landlordRepository.findById(id);
        if (existing.isPresent()) {
            Landlord l = existing.get();
            l.setFirstName(updated.getFirstName());
            l.setLastName(updated.getLastName());
            l.setPhone(updated.getPhone());
            l.setBuilding(updated.getBuilding());
            l.setPropertyTypes(updated.getPropertyTypes());
            return landlordRepository.save(l);
        }
        return null;
    }

    public boolean deleteLandlord(UUID id) {
        if (landlordRepository.existsById(id)) {
            landlordRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Landlord> getLandlordsByPropertyType(String name) {
        return landlordRepository.findByPropertyTypeNameIgnoreCase(name);
    }
    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    public ResponseEntity<String> addPropertyTypeToLandlord(UUID landlordId, UUID propertyTypeId) {
        Optional<Landlord> landlordOpt = landlordRepository.findById(landlordId);
        if (!landlordOpt.isPresent()) {
            return new ResponseEntity<>("The landlord with that id does not exist", HttpStatus.NOT_FOUND);
        }

        Optional<PropertyType> propertyTypeOpt = propertyTypeRepository.findById(propertyTypeId);
        if (!propertyTypeOpt.isPresent()) {
            return new ResponseEntity<>("That property type does not exist", HttpStatus.NOT_FOUND);
        }

        Landlord landlord = landlordOpt.get();
        PropertyType propertyType = propertyTypeOpt.get();

        if (landlord.getPropertyTypes().contains(propertyType)) {
            return new ResponseEntity<>("The landlord already has that property type", HttpStatus.CONFLICT);
        }

        landlord.getPropertyTypes().add(propertyType);
        landlordRepository.save(landlord);
        return new ResponseEntity<>("Property type added to the landlord", HttpStatus.OK);
    }

    public ResponseEntity<String> moveLandlordToBuilding(UUID landlordId, int buildingNumber) {
        Optional<Landlord> landlordOpt = landlordRepository.findById(landlordId);
        if (!landlordOpt.isPresent()) {
            return new ResponseEntity<>("The landlord with that id does not exist", HttpStatus.NOT_FOUND);
        }

        Optional<Building> buildingOpt = buildingRepository.findByBuildingNumber(buildingNumber);
        if (!buildingOpt.isPresent()) {
            return new ResponseEntity<>("The building with that buildingNumber does not exist", HttpStatus.NOT_FOUND);
        }

        Building newBuilding = buildingOpt.get();
        if (landlordRepository.existsByBuilding(newBuilding)) {
            return new ResponseEntity<>("That building is taken by another landlord", HttpStatus.CONFLICT);
        }

        Landlord landlord = landlordOpt.get();
        landlord.setBuilding(newBuilding);
        landlordRepository.save(landlord);
        return new ResponseEntity<>("Building changed successfully", HttpStatus.OK);
    }
}