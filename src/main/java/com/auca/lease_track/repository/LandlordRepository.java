package com.auca.lease_track.repository;

import com.auca.lease_track.model.Building;
import com.auca.lease_track.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, UUID> {
    @Query("SELECT l FROM Landlord l JOIN l.propertyTypes pt WHERE LOWER(pt.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Landlord> findByPropertyTypeNameIgnoreCase(@Param("name") String name);
    boolean existsByBuilding(Building building);
}