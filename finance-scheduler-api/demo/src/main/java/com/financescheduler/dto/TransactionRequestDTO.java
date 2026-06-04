package com.financescheduler.dto;

import java.time.LocalDate;
import java.util.Objects;

public final class TransactionRequestDTO {
    private final String origin;
    private final String destination;
    private final Double value;
    private final LocalDate transferDate;

    // Construtor
    public TransactionRequestDTO(String origin, String destination, Double value, LocalDate transferDate) {
        this.origin = origin;
        this.destination = destination;
        this.value = value;
        this.transferDate = transferDate;
    }

    // Métodos de leitura (padrão Record)
    public String origin() {
        return this.origin;
    }

    public String destination() {
        return this.destination;
    }

    public Double value() {
        return this.value;
    }

    public LocalDate transferDate() {
        return this.transferDate;
    }

    // Equals, HashCode e ToString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionRequestDTO that = (TransactionRequestDTO) o;
        return Objects.equals(origin, that.origin) && 
               Objects.equals(destination, that.destination) && 
               Objects.equals(value, that.value) && 
               Objects.equals(transferDate, that.transferDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, value, transferDate);
    }

    @Override
    public String toString() {
        return "TransactionRequestDTO{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", value=" + value +
                ", transferDate=" + transferDate +
                '}';
    }
}