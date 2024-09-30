package com.joancesar.pricecatalogservice.domain;

import jakarta.validation.constraints.NotNull;

public record ApplicablePriceDomain(
        @NotNull Product product,
        @NotNull Brand brand,
        @NotNull PriceRate priceRate
) {
}
