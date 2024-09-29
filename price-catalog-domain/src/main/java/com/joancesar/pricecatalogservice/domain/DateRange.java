package com.joancesar.pricecatalogservice.domain;

import com.joancesar.pricecatalogservice.exceptions.DomainException;

import java.time.LocalDateTime;

record DateRange(LocalDateTime startDate, LocalDateTime endDate) {
    public DateRange {
        if (startDate.isAfter(endDate)) {
            throw new DomainException("startDate debe ser antes o igual a endDate");
        }
    }

    public boolean contains(LocalDateTime date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}