package com.joancesar.pricecatalogservice.domain;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PriceCalculation(
        @NotNull LocalDateTime date,
        @NotNull Brand brand,
        @NotNull Product product
) {
}
