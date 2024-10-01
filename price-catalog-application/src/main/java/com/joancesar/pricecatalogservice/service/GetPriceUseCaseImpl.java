package com.joancesar.pricecatalogservice.service;

import com.joancesar.pricecatalogservice.domain.ApplicablePriceDomain;
import com.joancesar.pricecatalogservice.domain.PriceCalculation;
import com.joancesar.pricecatalogservice.output.PriceCacheRepositoryPort;
import com.joancesar.pricecatalogservice.output.PriceRepositoryPort;
import com.joancesar.pricecatalogservice.usecase.GetPriceUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetPriceUseCaseImpl implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;
    private final PriceCacheRepositoryPort priceCacheRepositoryPort;
    private static final String CACHE_NAME = "prices";

    public GetPriceUseCaseImpl(PriceRepositoryPort priceRepositoryPort,
                               PriceCacheRepositoryPort priceCacheRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
        this.priceCacheRepositoryPort = priceCacheRepositoryPort;
    }

    @Override
    public Optional<ApplicablePriceDomain> getPrice(PriceCalculation priceCalculation) {
        return priceCacheRepositoryPort.get(CACHE_NAME, priceCalculation.toString(), ApplicablePriceDomain.class)
                .or(() -> priceRepositoryPort.findApplicablePrices(priceCalculation.product().id(),
                                priceCalculation.brand().id(), priceCalculation.date())
                    .stream()
                    .max(ApplicablePriceDomain::compareByPriority)
                    .map(maxPriorityPrice -> {
                        priceCacheRepositoryPort.put(CACHE_NAME, priceCalculation.toString(), maxPriorityPrice);
                        return maxPriorityPrice;
                }));
    }
}
