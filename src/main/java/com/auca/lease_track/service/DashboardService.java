package com.auca.lease_track.service;

import com.auca.lease_track.model.LeaseStatus;
import com.auca.lease_track.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private RentPaymentRepository rentPaymentRepository;

    public Map<String, Long> getDashboardSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("tenants", tenantRepository.count());
        summary.put("landlords", landlordRepository.count());

        // Reuse C2 free buildings query count
        long freeBuildingsCount = buildingRepository.findFreeBuildings().size();
        summary.put("freeBuildings", freeBuildingsCount);

        long leasesStartingToday = leaseRepository.findByStartDateBetweenOrderByStartDateAsc(LocalDate.now(), LocalDate.now()).size();
        summary.put("leasesStartingToday", leasesStartingToday);

        long draftedCount = leaseRepository.findByStatusOrderByStartDateAsc(LeaseStatus.DRAFTED).size();
        summary.put("drafted", draftedCount);

        summary.put("rentPayments", rentPaymentRepository.count());

        return summary;
    }
}