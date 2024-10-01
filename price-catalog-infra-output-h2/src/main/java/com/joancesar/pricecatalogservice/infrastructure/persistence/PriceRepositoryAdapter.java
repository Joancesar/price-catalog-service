package com.joancesar.pricecatalogservice.infrastructure.persistence;

import com.joancesar.pricecatalogservice.domain.ApplicablePriceDomain;
import com.joancesar.pricecatalogservice.infrastructure.mapper.PriceMapper;
import com.joancesar.pricecatalogservice.infrastructure.repository.JpaPriceRepository;
import com.joancesar.pricecatalogservice.output.PriceRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final JpaPriceRepository jpaPriceRepository;
    private final PriceMapper priceMapper;

    public PriceRepositoryAdapter(JpaPriceRepository jpaPriceRepository, PriceMapper priceMapper) {
        this.jpaPriceRepository = jpaPriceRepository;
        this.priceMapper = priceMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ApplicablePriceDomain> findApplicablePrices(Long productId, Long brandId, LocalDateTime applicationDate) {
        return jpaPriceRepository.findApplicablePrices(productId, brandId, applicationDate)
                .stream().map(priceMapper::toDomain).toList();
    }
}
