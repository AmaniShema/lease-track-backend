package com.auca.lease_track.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rent_payment")
public class RentPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String monthCovered;
    private String reference;
    private int amount;
    private LocalDate paidDate;

    @ManyToOne
    @JoinColumn(name = "lease_id")
    private Lease lease;

    public RentPayment() {}

    public RentPayment(String monthCovered, String reference, int amount, LocalDate paidDate, Lease lease) {
        this.monthCovered = monthCovered;
        this.reference = reference;
        this.amount = amount;
        this.paidDate = paidDate;
        this.lease = lease;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getMonthCovered() { return monthCovered; }
    public void setMonthCovered(String monthCovered) { this.monthCovered = monthCovered; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }

    public Lease getLease() { return lease; }
    public void setLease(Lease lease) { this.lease = lease; }
}