package com.auca.lease_track.repository;

import com.auca.lease_track.model.RentPayment;
import com.auca.lease_track.model.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RentPaymentRepository extends JpaRepository<RentPayment, UUID> {

    // E2: JPQL path navigation through lease and tenant's nationalId
    @Query("SELECT rp FROM RentPayment rp WHERE rp.lease.tenant.nationalId = :nationalId ORDER BY rp.paidDate DESC")
    List<RentPayment> findByTenantNationalIdOrderByPaidDateDesc(@Param("nationalId") String nationalId);

    // E3: Check if a lease has any rent payments before deletion
    boolean existsByLease(Lease lease);
}