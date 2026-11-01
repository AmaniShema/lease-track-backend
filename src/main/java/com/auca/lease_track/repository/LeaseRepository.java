package com.auca.lease_track.repository;

import com.auca.lease_track.model.Landlord;
import com.auca.lease_track.model.Lease;
import com.auca.lease_track.model.LeaseStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, UUID> {
    List<Lease> findByStatusOrderByStartDateAsc(LeaseStatus status);

    List<Lease> findByStartDateBetweenOrderByStartDateAsc(LocalDate start, LocalDate end);

    boolean existsByLandlordAndStartDateAndStatusNot(Landlord landlord, LocalDate startDate, LeaseStatus status);
    @Query("SELECT l.status, COUNT(l) FROM Lease l GROUP BY l.status")
    List<Object[]> getLeaseStatsByStatus();

    @Modifying
    @Transactional
    @Query("UPDATE Lease l SET l.status = 'TERMINATED' WHERE l.landlord.id = :landlordId AND l.startDate = :date AND l.status <> 'ACTIVE'")
    int cancelLeasesForLandlordOnDate(@Param("landlordId") UUID landlordId, @Param("date") LocalDate date);
}