package com.financescheduler.dto;

import java.time.LocalDate;

public record TaxaRequestDTO(
    LocalDate transferDate,
    Double value
) {}