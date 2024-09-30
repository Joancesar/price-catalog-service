package com.joancesar.pricecatalogservice.output;

import com.joancesar.pricecatalogservice.domain.ApplicablePriceDomain;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {

    Optional<ApplicablePriceDomain> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
