package com.auca.lease_track.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int buildingNumber;
    private String name;

    public Building() {}

    public Building(int buildingNumber, String name) {
        this.buildingNumber = buildingNumber;
        this.name = name;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public int getBuildingNumber() { return buildingNumber; }
    public void setBuildingNumber(int buildingNumber) { this.buildingNumber = buildingNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}