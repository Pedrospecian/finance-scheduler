package com.financescheduler.model;
 
import jakarta.persistence.*;
import com.financescheduler.model.Account;
import java.time.LocalDate;
 
@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origin_account_id", nullable = false)
    private Account origin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_account_id", nullable = false)
    private Account destination;

    @Column(name = "`value`", nullable = false)
    private Double value;

    @Column(nullable = false)
    private Double transferRate;

    @Column(nullable = false)
    private LocalDate transferDate;

    @Column(nullable = false)
    private LocalDate createdAt;

    public Transaction() {}

    public Transaction(Account origin, Account destination, Double value, Double transferRate, LocalDate transferDate, LocalDate createdAt) {
        this.origin = origin;
        this.destination = destination;
        this.value = value;
        this.transferRate = transferRate;
        this.transferDate = transferDate;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(Double transferRate) {
        this.transferRate = transferRate;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}