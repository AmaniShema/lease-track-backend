package com.auca.lease_track.service;

import com.auca.lease_track.model.PropertyType;
import com.auca.lease_track.repository.PropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyTypeService {

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    public PropertyType savePropertyType(PropertyType propertyType) {
        return propertyTypeRepository.save(propertyType);
    }

    public List<PropertyType> getAllPropertyTypes() {
        return propertyTypeRepository.findAll();
    }

    public Optional<PropertyType> getPropertyTypeById(UUID id) {
        return propertyTypeRepository.findById(id);
    }

    public PropertyType updatePropertyType(UUID id, PropertyType updated) {
        Optional<PropertyType> existing = propertyTypeRepository.findById(id);
        if (existing.isPresent()) {
            PropertyType pt = existing.get();
            pt.setName(updated.getName());
            return propertyTypeRepository.save(pt);
        }
        return null;
    }

    public boolean deletePropertyType(UUID id) {
        if (propertyTypeRepository.existsById(id)) {
            propertyTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<PropertyType> getUnusedPropertyTypes() {
        return propertyTypeRepository.findUnusedPropertyTypes();
    }
}