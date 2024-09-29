package com.joancesar.pricecatalogservice.domain;

import java.time.LocalDateTime;

public record PriceDomain(
        Integer priceList,
        Brand brand,
        DateRange dateRange,
        Product product,
        Priority priority,
        Money price) {

    public boolean isApplicable(LocalDateTime date) {
        return dateRange.contains(date);
    }

    public boolean hasHigherPriorityThan(PriceDomain other) {
        return this.priority.isHigherThan(other.priority);
    }

}
