package com.financescheduler.dto;

import java.time.LocalDate;

public record TransactionRequestDTO(
    String origin,
    String destination,
    Double value,
    LocalDate transferDate
) {}