package com.auca.lease_track.service;

import com.auca.lease_track.model.Lease;
import com.auca.lease_track.model.LeaseStatus;
import com.auca.lease_track.model.Landlord;
import com.auca.lease_track.repository.LeaseRepository;
import com.auca.lease_track.repository.LandlordRepository;
import com.auca.lease_track.repository.RentPaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    public ResponseEntity<String> saveLease(Lease lease) {
        if (lease.getStartDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>("Lease date cannot be in the past", HttpStatus.BAD_REQUEST);
        }

        // Fetch landlord from database to ensure existence and relationship
        Optional<Landlord> landlordOpt = landlordRepository.findById(lease.getLandlord().getId());
        if (landlordOpt.isPresent()) {
            lease.setLandlord(landlordOpt.get());
        }

        boolean hasActiveLease = leaseRepository.existsByLandlordAndStartDateAndStatusNot(
                lease.getLandlord(), lease.getStartDate(), LeaseStatus.TERMINATED
        );

        if (hasActiveLease) {
            return new ResponseEntity<>("Landlord already has a lease starting on that date", HttpStatus.CONFLICT);
        }

        // A new lease always starts as DRAFTED
        lease.setStatus(LeaseStatus.DRAFTED);
        leaseRepository.save(lease);
        return new ResponseEntity<>("Lease saved successfully", HttpStatus.CREATED);
    }

    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }

    public Optional<Lease> getLeaseById(UUID id) {
        return leaseRepository.findById(id);
    }

    public Lease updateLease(UUID id, Lease updated) {
        Optional<Lease> existing = leaseRepository.findById(id);
        if (existing.isPresent()) {
            Lease l = existing.get();
            l.setStartDate(updated.getStartDate());
            // Note: PUT general update no longer changes status per D2 rules
            l.setUnitNumber(updated.getUnitNumber());
            l.setMonthlyRent(updated.getMonthlyRent());
            l.setTenant(updated.getTenant());
            l.setLandlord(updated.getLandlord());
            return leaseRepository.save(l);
        }
        return null;
    }

    public ResponseEntity<String> updateLeaseStatus(UUID id, LeaseStatus newStatus) {
        Optional<Lease> existingOpt = leaseRepository.findById(id);
        if (!existingOpt.isPresent()) {
            return new ResponseEntity<>("The lease with that id does not exist", HttpStatus.NOT_FOUND);
        }

        Lease lease = existingOpt.get();
        LeaseStatus currentStatus = lease.getStatus();

        boolean allowed = false;
        if (currentStatus == LeaseStatus.DRAFTED) {
            if (newStatus == LeaseStatus.SIGNED || newStatus == LeaseStatus.TERMINATED) {
                allowed = true;
            }
        } else if (currentStatus == LeaseStatus.SIGNED) {
            if (newStatus == LeaseStatus.ACTIVE || newStatus == LeaseStatus.TERMINATED) {
                allowed = true;
            }
        }
        // ACTIVE and TERMINATED allow no moves

        if (!allowed) {
            return new ResponseEntity<>("Cannot change status from " + currentStatus + " to " + newStatus, HttpStatus.BAD_REQUEST);
        }

        lease.setStatus(newStatus);
        leaseRepository.save(lease);
        return new ResponseEntity<>("Lease status updated successfully", HttpStatus.OK);
    }

    @Autowired
    private RentPaymentRepository rentPaymentRepository;

    public ResponseEntity<String> deleteLeaseProtected(UUID id) {
        Optional<Lease> leaseOpt = leaseRepository.findById(id);
        if (!leaseOpt.isPresent()) {
            return new ResponseEntity<>("The lease with that id does not exist", HttpStatus.NOT_FOUND);
        }

        Lease lease = leaseOpt.get();
        if (rentPaymentRepository.existsByLease(lease)) {
            return new ResponseEntity<>("That lease has rent payments, delete them first", HttpStatus.CONFLICT);
        }

        leaseRepository.delete(lease);
        return new ResponseEntity<>("Lease deleted successfully", HttpStatus.OK);
    }

    public List<Lease> getLeasesByStatus(LeaseStatus status) {
        return leaseRepository.findByStatusOrderByStartDateAsc(status);
    }

    public List<Lease> getLeasesBetweenDates(LocalDate start, LocalDate end) {
        return leaseRepository.findByStartDateBetweenOrderByStartDateAsc(start, end);
    }
    public List<Object[]> getLeaseStatsByStatus() {
        return leaseRepository.getLeaseStatsByStatus();
    }

    @Transactional
    public ResponseEntity<String> cancelLeasesForLandlordOnDate(UUID landlordId, LocalDate date) {
        int updatedCount = leaseRepository.cancelLeasesForLandlordOnDate(landlordId, date);
        return new ResponseEntity<>(updatedCount + " leases cancelled", HttpStatus.OK);
    }
    public Page<Lease> getLeasesPaginated(Pageable pageable) {
        return leaseRepository.findAll(pageable);
    }
}