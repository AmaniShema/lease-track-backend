package com.auca.lease_track.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "landlord")
public class Landlord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = true)
    private Building building;

    @ManyToMany
    @JoinTable(
            name = "landlord_property_type",
            joinColumns = @JoinColumn(name = "landlord_id"),
            inverseJoinColumns = @JoinColumn(name = "property_type_id")
    )
    private List<PropertyType> propertyTypes;

    public Landlord() {}

    public Landlord(String firstName, String lastName, String phone, Building building, List<PropertyType> propertyTypes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.building = building;
        this.propertyTypes = propertyTypes;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Building getBuilding() { return building; }
    public void setBuilding(Building building) { this.building = building; }

    public List<PropertyType> getPropertyTypes() { return propertyTypes; }
    public void setPropertyTypes(List<PropertyType> propertyTypes) { this.propertyTypes = propertyTypes; }
}