package com.auca.lease_track.controller;

import com.auca.lease_track.model.Lease;
import com.auca.lease_track.model.LeaseStatus;
import com.auca.lease_track.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    @PostMapping("/save")
    public ResponseEntity<String> saveLease(@RequestBody Lease lease) {
        return leaseService.saveLease(lease);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateLeaseStatus(@PathVariable UUID id, @RequestParam LeaseStatus status) {
        return leaseService.updateLeaseStatus(id, status);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Lease>> getAllLeases() {
        return new ResponseEntity<>(leaseService.getAllLeases(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLeaseById(@PathVariable UUID id) {
        Optional<Lease> l = leaseService.getLeaseById(id);
        if (l.isPresent()) {
            return new ResponseEntity<>(l.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The lease with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLease(@PathVariable UUID id, @RequestBody Lease lease) {
        Lease updated = leaseService.updateLease(id, lease);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>("The lease with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLease(@PathVariable UUID id) {
        return leaseService.deleteLeaseProtected(id);
    }
    @GetMapping("/by-status")
    public ResponseEntity<List<Lease>> getLeasesByStatus(@RequestParam LeaseStatus status) {
        List<Lease> leases = leaseService.getLeasesByStatus(status);
        return new ResponseEntity<>(leases, HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Lease>> getLeasesBetweenDates(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        List<Lease> leases = leaseService.getLeasesBetweenDates(start, end);
        return new ResponseEntity<>(leases, HttpStatus.OK);
    }
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getLeaseStatsByStatus() {
        List<Object[]> stats = leaseService.getLeaseStatsByStatus();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelLeasesForLandlordOnDate(@RequestParam UUID landlordId, @RequestParam LocalDate date) {
        return leaseService.cancelLeasesForLandlordOnDate(landlordId, date);
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Lease>> getLeasesPaginated(Pageable pageable) {
        Page<Lease> page = leaseService.getLeasesPaginated(pageable);
        return ResponseEntity.ok(page);
    }

}