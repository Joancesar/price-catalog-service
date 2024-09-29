package com.joancesar.pricecatalogservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.joancesar.pricecatalogservice.exceptions.DomainException;

class PriceDomainTest {

    @Test
    void isApplicable_withDateRangeApplicable_thenReturnTrue() {
        LocalDateTime now = LocalDateTime.now();
        DateRange dateRange = new DateRange(now.minusDays(1), now.plusDays(1));
        PriceDomain priceDomain = new PriceDomain(1, new Brand(1), dateRange,
                new Product(1), new Priority(1), new Money(BigDecimal.TEN, "USD"));

        assertTrue(priceDomain.isApplicable(now));
    }

    @Test
    void isApplicable_withDateRangeNotApplicable_thenReturnFalse() {
        LocalDateTime now = LocalDateTime.now();
        DateRange dateRange = new DateRange(now.minusDays(1), now.plusDays(1));
        PriceDomain priceDomain = new PriceDomain(1, new Brand(1), dateRange,
                new Product(1), new Priority(1), new Money(BigDecimal.TEN, "USD"));

        assertFalse(priceDomain.isApplicable(now.minusDays(2)));
    }

    @Test
    void hasHigherPriorityThan_withHigherPriority_thenReturnTrue() {
        Priority priority1 = new Priority(1);
        Priority priority2 = new Priority(2);
        PriceDomain priceDomain1 = new PriceDomain(1,
                new Brand(1), new DateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new Product(1), priority1, new Money(BigDecimal.TEN, "USD"));
        PriceDomain priceDomain2 = new PriceDomain(2, new Brand(2),
                new DateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new Product(2), priority2, new Money(BigDecimal.TEN, "USD"));

        assertTrue(priceDomain2.hasHigherPriorityThan(priceDomain1));
    }

    @Test
    void hasHigherPriorityThan_withLowerPriority_thenReturnFalse() {
        Priority priority1 = new Priority(1);
        Priority priority2 = new Priority(2);
        PriceDomain priceDomain1 = new PriceDomain(1, new Brand(1),
                new DateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new Product(1), priority1, new Money(BigDecimal.TEN, "USD"));
        PriceDomain priceDomain2 = new PriceDomain(2, new Brand(2),
                new DateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new Product(2), priority2, new Money(BigDecimal.TEN, "USD"));

        assertFalse(priceDomain1.hasHigherPriorityThan(priceDomain2));
    }

    @Test
    void dateRangeValidation_withInvalidDateRange_thenThrowException() {
        LocalDateTime now = LocalDateTime.now();
        assertThrows(DomainException.class, () -> new DateRange(now.plusDays(1), now));
    }

    @Test
    void priorityValidation_withInvalidPriority_thenThrowException() {
        assertThrows(DomainException.class, () -> new Priority(-1));
        assertThrows(DomainException.class, () -> new Priority(null));
    }

    @Test
    void moneyValidation_withInvalidMoney_thenThrowException() {
        assertThrows(DomainException.class, () -> new Money(BigDecimal.valueOf(-1), "USD"));
        assertThrows(DomainException.class, () -> new Money(BigDecimal.TEN, "INVALID"));
        assertThrows(DomainException.class, () -> new Money(new BigDecimal("10.123"), "USD"));
    }
}
