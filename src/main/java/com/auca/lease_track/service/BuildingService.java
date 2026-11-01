package com.auca.lease_track.service;

import com.auca.lease_track.model.Building;
import com.auca.lease_track.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    public Building saveBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Optional<Building> getBuildingById(UUID id) {
        return buildingRepository.findById(id);
    }

    public Building updateBuilding(UUID id, Building updated) {
        Optional<Building> existing = buildingRepository.findById(id);
        if (existing.isPresent()) {
            Building b = existing.get();
            b.setBuildingNumber(updated.getBuildingNumber());
            b.setName(updated.getName());
            return buildingRepository.save(b);
        }
        return null;
    }

    public boolean deleteBuilding(UUID id) {
        if (buildingRepository.existsById(id)) {
            buildingRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Building> getFreeBuildings() {
        return buildingRepository.findFreeBuildings();
    }
}