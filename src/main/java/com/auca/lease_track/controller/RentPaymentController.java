package com.auca.lease_track.controller;

import com.auca.lease_track.model.RentPayment;
import com.auca.lease_track.service.RentPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rent-payments")
public class RentPaymentController {

    @Autowired
    private RentPaymentService rentPaymentService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRentPayment(@RequestParam UUID leaseId, @RequestBody RentPayment rentPayment) {
        return rentPaymentService.saveRentPayment(leaseId, rentPayment);
    }

    @GetMapping("/tenant/{nationalId}")
    public ResponseEntity<List<RentPayment>> getRentPaymentsByTenant(@PathVariable String nationalId) {
        List<RentPayment> payments = rentPaymentService.getRentPaymentsByTenantNationalId(nationalId);
        return ResponseEntity.ok(payments);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRentPayment(@PathVariable UUID id) {
        return rentPaymentService.deleteRentPayment(id);
    }
}