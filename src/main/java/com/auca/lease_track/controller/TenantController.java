package com.auca.lease_track.controller;

import com.auca.lease_track.model.Tenant;
import com.auca.lease_track.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTenant(@RequestBody Tenant tenant) {
        tenantService.saveTenant(tenant);
        return new ResponseEntity<>("Tenant saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tenant>> getAllTenants() {
        return new ResponseEntity<>(tenantService.getAllTenants(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTenantById(@PathVariable UUID id) {
        Optional<Tenant> t = tenantService.getTenantById(id);
        if (t.isPresent()) {
            return new ResponseEntity<>(t.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The tenant with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTenant(@PathVariable UUID id, @RequestBody Tenant tenant) {
        Tenant updated = tenantService.updateTenant(id, tenant);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>("The tenant with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTenant(@PathVariable UUID id) {
        boolean deleted = tenantService.deleteTenant(id);
        if (deleted) {
            return new ResponseEntity<>("Tenant deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("The tenant with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Tenant>> getTenantsByLastName(@RequestParam String lastName) {
        List<Tenant> tenants = tenantService.getTenantsByLastName(lastName);
        return new ResponseEntity<>(tenants, HttpStatus.OK);
    }
    @GetMapping("/of-landlord/{landlordId}")
    public ResponseEntity<?> getTenantsOfLandlord(@PathVariable UUID landlordId) {
        return (ResponseEntity<?>) tenantService.getTenantsOfLandlord(landlordId);
    }
}