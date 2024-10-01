package com.joancesar.pricecatalogservice.output;

import com.joancesar.pricecatalogservice.domain.ApplicablePriceDomain;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {

    List<ApplicablePriceDomain> findApplicablePrices(Long productId, Long brandId, LocalDateTime applicationDate);
}
