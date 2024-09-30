package com.joancesar.pricecatalogservice.infrastructure.persistence;

import com.joancesar.pricecatalogservice.domain.ApplicablePriceDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PriceRepositoryAdapterIT {

    @Autowired
    private PriceRepositoryAdapter priceRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void whenFindApplicablePriceCalledWithExistingDataThenPriceIsReturned() {
        long productId = 35455L;
        long brandId = 1L;
        LocalDateTime appliedDate = LocalDateTime.parse("2020-06-14 10:00:00", formatter);

        Optional<ApplicablePriceDomain> result = priceRepository.findApplicablePrice(productId, brandId, appliedDate);
        assertTrue(result.isPresent());
        assertEquals(brandId, result.get().brand().id());
        assertEquals(productId, result.get().product().id());
        assertEquals(new BigDecimal("35.50"), result.get().priceRate().price());
    }

    @Test
    void whenFindApplicablePriceCalledWithNonExistingDataThenNoPriceIsReturned() {
        long productId = 35455L;
        long brandId = 1L;
        LocalDateTime appliedDate = LocalDateTime.parse("2021-01-01 10:00:00", formatter);

        Optional<ApplicablePriceDomain> result = priceRepository.findApplicablePrice(productId, brandId, appliedDate);

        assertTrue(result.isEmpty());
    }
}
