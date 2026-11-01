package com.auca.lease_track.service;

import com.auca.lease_track.model.Tenant;
import com.auca.lease_track.repository.LandlordRepository;
import com.auca.lease_track.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Optional<Tenant> getTenantById(UUID id) {
        return tenantRepository.findById(id);
    }

    public Tenant updateTenant(UUID id, Tenant updated) {
        Optional<Tenant> existing = tenantRepository.findById(id);
        if (existing.isPresent()) {
            Tenant t = existing.get();
            t.setFirstName(updated.getFirstName());
            t.setLastName(updated.getLastName());
            t.setNationalId(updated.getNationalId());
            t.setPhone(updated.getPhone());
            t.setEmail(updated.getEmail());
            return tenantRepository.save(t);
        }
        return null;
    }

    public boolean deleteTenant(UUID id) {
        if (tenantRepository.existsById(id)) {
            tenantRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Tenant> getTenantsByLastName(String lastName) {
        return tenantRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
    @Autowired
    private LandlordRepository landlordRepository;

    public ResponseEntity<?> getTenantsOfLandlord(UUID landlordId) {
        if (!landlordRepository.existsById(landlordId)) {
            return new ResponseEntity<>("The landlord with that id does not exist", HttpStatus.NOT_FOUND);
        }
        List<Tenant> tenants = tenantRepository.findTenantsByLandlordId(landlordId);
        return new ResponseEntity<>(tenants, HttpStatus.OK);
    }
}