package com.joancesar.pricecatalogservice.domain;

import com.joancesar.pricecatalogservice.exceptions.DomainException;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DateRange(@NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate) {
    public DateRange {
        if (startDate.isAfter(endDate)) {
            throw new DomainException("startDate debe ser antes o igual a endDate");
        }
    }
}