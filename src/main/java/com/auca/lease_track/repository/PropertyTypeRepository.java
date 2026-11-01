package com.auca.lease_track.repository;

import com.auca.lease_track.model.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyType, UUID> {
    @Query("SELECT pt FROM PropertyType pt WHERE pt.landlords IS EMPTY")
    List<PropertyType> findUnusedPropertyTypes();
}