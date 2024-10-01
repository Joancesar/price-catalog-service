package com.joancesar.pricecatalogservice.infrastructure.persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.joancesar.pricecatalogservice.domain.ApplicablePriceDomain;

@SpringBootTest
class PriceRepositoryAdapterIT {

    @Autowired
    private PriceRepositoryAdapter priceRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void whenFindApplicablePricesCalledWithExistingDataThenPricesAreReturned() {
        long productId = 35455L;
        long brandId = 1L;
        LocalDateTime appliedDate = LocalDateTime.parse("2020-06-14 10:00:00", formatter);

        List<ApplicablePriceDomain> result = priceRepository.findApplicablePrices(productId, brandId, appliedDate);
        assertFalse(result.isEmpty());
        assertEquals(brandId, result.getFirst().brand().id());
        assertEquals(productId, result.getFirst().product().id());
    }

    @Test
    void whenFindApplicablePricesCalledWithNonExistingDataThenNoPricesAreReturned() {
        long productId = 35455L;
        long brandId = 1L;
        LocalDateTime appliedDate = LocalDateTime.parse("2021-01-01 10:00:00", formatter);

        List<ApplicablePriceDomain> result = priceRepository.findApplicablePrices(productId, brandId, appliedDate);
        assertTrue(result.isEmpty());
    }
}
