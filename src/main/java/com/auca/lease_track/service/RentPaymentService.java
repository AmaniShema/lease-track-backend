package com.auca.lease_track.service;

import com.auca.lease_track.model.Lease;
import com.auca.lease_track.model.LeaseStatus;
import com.auca.lease_track.model.RentPayment;
import com.auca.lease_track.repository.LeaseRepository;
import com.auca.lease_track.repository.RentPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentPaymentService {

    @Autowired
    private RentPaymentRepository rentPaymentRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    public ResponseEntity<String> saveRentPayment(UUID leaseId, RentPayment rentPayment) {
        Optional<Lease> leaseOpt = leaseRepository.findById(leaseId);
        if (!leaseOpt.isPresent()) {
            return new ResponseEntity<>("The lease with that id does not exist", HttpStatus.NOT_FOUND);
        }

        Lease lease = leaseOpt.get();
        if (lease.getStatus() != LeaseStatus.ACTIVE) {
            return new ResponseEntity<>("Rent payments are only for ACTIVE leases", HttpStatus.BAD_REQUEST);
        }

        rentPayment.setLease(lease);
        rentPayment.setPaidDate(LocalDate.now());
        rentPaymentRepository.save(rentPayment);
        return new ResponseEntity<>("Rent payment saved successfully", HttpStatus.CREATED);
    }

    public List<RentPayment> getRentPaymentsByTenantNationalId(String nationalId) {
        return rentPaymentRepository.findByTenantNationalIdOrderByPaidDateDesc(nationalId);
    }

    public ResponseEntity<String> deleteRentPayment(UUID id) {
        if (!rentPaymentRepository.existsById(id)) {
            return new ResponseEntity<>("The rent payment with that id does not exist", HttpStatus.NOT_FOUND);
        }
        rentPaymentRepository.deleteById(id);
        return new ResponseEntity<>("Rent payment deleted successfully", HttpStatus.OK);
    }
}