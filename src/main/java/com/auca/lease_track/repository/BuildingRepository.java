package com.auca.lease_track.repository;

import com.auca.lease_track.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {
    @Query("SELECT b FROM Building b WHERE NOT EXISTS (SELECT l FROM Landlord l WHERE l.building = b) ORDER BY b.buildingNumber ASC")
    List<Building> findFreeBuildings();
    Optional<Building> findByBuildingNumber(int buildingNumber);
}
