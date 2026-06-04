package com.financescheduler.dto;

import java.time.LocalDate;
import java.util.Objects;

public final class TaxaRequestDTO {
    private final LocalDate transferDate;
    private final Double value;

    public TaxaRequestDTO(LocalDate transferDate, Double value) {
        this.transferDate = transferDate;
        this.value = value;
    }

    public LocalDate transferDate() {
        return this.transferDate;
    }

    public Double value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxaRequestDTO that = (TaxaRequestDTO) o;
        return Objects.equals(transferDate, that.transferDate) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transferDate, value);
    }

    @Override
    public String toString() {
        return "TaxaRequestDTO{" +
                "transferDate=" + transferDate +
                ", value=" + value +
                '}';
    }
}