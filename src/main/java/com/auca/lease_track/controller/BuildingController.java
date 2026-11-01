package com.auca.lease_track.controller;

import com.auca.lease_track.model.Building;
import com.auca.lease_track.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/buildings")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @PostMapping("/save")
    public ResponseEntity<String> saveBuilding(@RequestBody Building building) {
        buildingService.saveBuilding(building);
        return new ResponseEntity<>("Building saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Building>> getAllBuildings() {
        return new ResponseEntity<>(buildingService.getAllBuildings(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBuildingById(@PathVariable UUID id) {
        Optional<Building> b = buildingService.getBuildingById(id);
        if (b.isPresent()) {
            return new ResponseEntity<>(b.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The building with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBuilding(@PathVariable UUID id, @RequestBody Building building) {
        Building updated = buildingService.updateBuilding(id, building);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>("The building with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBuilding(@PathVariable UUID id) {
        boolean deleted = buildingService.deleteBuilding(id);
        if (deleted) {
            return new ResponseEntity<>("Building deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("The building with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/free")
    public ResponseEntity<List<Building>> getFreeBuildings() {
        List<Building> buildings = buildingService.getFreeBuildings();
        return new ResponseEntity<>(buildings, HttpStatus.OK);
    }
}