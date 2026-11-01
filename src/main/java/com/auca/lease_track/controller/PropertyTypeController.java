package com.auca.lease_track.controller;

import com.auca.lease_track.model.PropertyType;
import com.auca.lease_track.service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/property-types")
public class PropertyTypeController {

    @Autowired
    private PropertyTypeService propertyTypeService;

    @PostMapping("/save")
    public ResponseEntity<String> savePropertyType(@RequestBody PropertyType propertyType) {
        propertyTypeService.savePropertyType(propertyType);
        return new ResponseEntity<>("Property type saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PropertyType>> getAllPropertyTypes() {
        return new ResponseEntity<>(propertyTypeService.getAllPropertyTypes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyTypeById(@PathVariable UUID id) {
        Optional<PropertyType> pt = propertyTypeService.getPropertyTypeById(id);
        if (pt.isPresent()) {
            return new ResponseEntity<>(pt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The property type with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePropertyType(@PathVariable UUID id, @RequestBody PropertyType propertyType) {
        PropertyType updated = propertyTypeService.updatePropertyType(id, propertyType);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>("The property type with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePropertyType(@PathVariable UUID id) {
        boolean deleted = propertyTypeService.deletePropertyType(id);
        if (deleted) {
            return new ResponseEntity<>("Property type deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("The property type with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/unused")
    public ResponseEntity<List<PropertyType>> getUnusedPropertyTypes() {
        List<PropertyType> propertyTypes = propertyTypeService.getUnusedPropertyTypes();
        return new ResponseEntity<>(propertyTypes, HttpStatus.OK);
    }
}