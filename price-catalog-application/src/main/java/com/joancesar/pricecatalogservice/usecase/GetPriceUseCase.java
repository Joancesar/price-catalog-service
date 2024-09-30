package com.joancesar.pricecatalogservice.usecase;

import com.joancesar.pricecatalogservice.domain.ApplicablePriceDomain;
import com.joancesar.pricecatalogservice.domain.PriceCalculation;

import java.util.Optional;

public interface GetPriceUseCase {

    Optional<ApplicablePriceDomain> getPrice(PriceCalculation priceCalculation);

}
