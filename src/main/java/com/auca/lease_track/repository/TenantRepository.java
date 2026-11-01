package com.auca.lease_track.repository;

import com.auca.lease_track.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    List<Tenant> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
    @Query("SELECT DISTINCT t FROM Lease l JOIN l.tenant t WHERE l.landlord.id = :landlordId")
    List<Tenant> findTenantsByLandlordId(@Param("landlordId") UUID landlordId);
}