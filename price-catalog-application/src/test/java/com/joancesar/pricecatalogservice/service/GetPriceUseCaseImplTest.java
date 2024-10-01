package com.joancesar.pricecatalogservice.service;

import com.joancesar.pricecatalogservice.domain.ApplicablePriceDomain;
import com.joancesar.pricecatalogservice.domain.Brand;
import com.joancesar.pricecatalogservice.domain.DateRange;
import com.joancesar.pricecatalogservice.domain.PriceCalculation;
import com.joancesar.pricecatalogservice.domain.PriceRate;
import com.joancesar.pricecatalogservice.domain.Product;
import com.joancesar.pricecatalogservice.output.PriceCacheRepositoryPort;
import com.joancesar.pricecatalogservice.output.PriceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPriceUseCaseImplTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @Mock
    private PriceCacheRepositoryPort priceCacheRepositoryPort;

    @InjectMocks
    private GetPriceUseCaseImpl getPriceUseCase;

    @Test
    void getPrice_whenPriceInCache_ThenReturnPriceFromCache() {
        PriceCalculation priceCalculation = new PriceCalculation(LocalDateTime.now(), new Brand(1L), new Product(1L));
        ApplicablePriceDomain cachedPrice = new ApplicablePriceDomain(
                new Product(1L),
                new Brand(1L),
                new PriceRate(1, new DateRange(LocalDateTime.now(), LocalDateTime.now()), new BigDecimal(1)),
                1
        );

        when(priceCacheRepositoryPort.get(anyString(), anyString(), eq(ApplicablePriceDomain.class)))
                .thenReturn(Optional.of(cachedPrice));

        Optional<ApplicablePriceDomain> result = getPriceUseCase.getPrice(priceCalculation);

        assertTrue(result.isPresent());
        assertEquals(cachedPrice, result.get());
        verify(priceCacheRepositoryPort, times(1)).get(anyString(), anyString(), eq(ApplicablePriceDomain.class));
        verify(priceRepositoryPort, never()).findApplicablePrices(anyLong(), anyLong(), any(LocalDateTime.class));
    }

    @Test
    void getPrice_whenPriceNotInCache_ThenReturnPriceFromRepository() {
        PriceCalculation priceCalculation = new PriceCalculation(LocalDateTime.now(), new Brand(1L), new Product(1L));
        ApplicablePriceDomain repositoryPrice = new ApplicablePriceDomain(
                new Product(1L),
                new Brand(1L),
                new PriceRate(1, new DateRange(LocalDateTime.now(), LocalDateTime.now()), new BigDecimal(1)),
                1
        );

        when(priceCacheRepositoryPort.get(anyString(), anyString(), eq(ApplicablePriceDomain.class)))
                .thenReturn(Optional.empty());
        when(priceRepositoryPort.findApplicablePrices(anyLong(), anyLong(), any(LocalDateTime.class)))
                .thenReturn(List.of(repositoryPrice));

        Optional<ApplicablePriceDomain> result = getPriceUseCase.getPrice(priceCalculation);

        assertTrue(result.isPresent());
        assertEquals(repositoryPrice, result.get());
        verify(priceCacheRepositoryPort, times(1)).get(anyString(), anyString(), eq(ApplicablePriceDomain.class));
        verify(priceRepositoryPort, times(1)).findApplicablePrices(anyLong(), anyLong(), any(LocalDateTime.class));
        verify(priceCacheRepositoryPort, times(1)).put(anyString(), anyString(), eq(repositoryPrice));
    }

    @Test
    void getPrice_whenNoPriceFound_ThenReturnEmpty() {
        PriceCalculation priceCalculation = new PriceCalculation(LocalDateTime.now(), new Brand(1L), new Product(1L));

        when(priceCacheRepositoryPort.get(anyString(), anyString(), eq(ApplicablePriceDomain.class)))
                .thenReturn(Optional.empty());
        when(priceRepositoryPort.findApplicablePrices(anyLong(), anyLong(), any(LocalDateTime.class)))
                .thenReturn(List.of());

        Optional<ApplicablePriceDomain> result = getPriceUseCase.getPrice(priceCalculation);

        assertTrue(result.isEmpty());
        verify(priceCacheRepositoryPort, times(1)).get(anyString(), anyString(), eq(ApplicablePriceDomain.class));
        verify(priceRepositoryPort, times(1)).findApplicablePrices(anyLong(), anyLong(), any(LocalDateTime.class));
    }
}
