package com.auca.lease_track.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "property_type")
public class PropertyType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "propertyTypes")
    @JsonIgnore
    private List<Landlord> landlords;

    public PropertyType() {}

    public PropertyType(String name) {
        this.name = name;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Landlord> getLandlords() { return landlords; }
    public void setLandlords(List<Landlord> landlords) { this.landlords = landlords; }
}