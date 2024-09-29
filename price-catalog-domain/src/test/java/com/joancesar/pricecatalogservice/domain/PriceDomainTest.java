package com.joancesar.pricecatalogservice.domain;

import com.joancesar.pricecatalogservice.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceDomainTest {

    @Test
    void isApplicable_withDateRangeApplicable_thenReturnTrue() {
        LocalDateTime now = LocalDateTime.now();
        PriceDomain.DateRange dateRange = new PriceDomain.DateRange(now.minusDays(1), now.plusDays(1));
        PriceDomain priceDomain = new PriceDomain("brand1", dateRange, 1, 1, new PriceDomain.Priority(1), new PriceDomain.Money(BigDecimal.TEN, "USD"));

        assertTrue(priceDomain.isApplicable(now));
    }

    @Test
    void isApplicable_withDateRangeNotApplicable_thenReturnFalse() {
        LocalDateTime now = LocalDateTime.now();
        PriceDomain.DateRange dateRange = new PriceDomain.DateRange(now.minusDays(1), now.plusDays(1));
        PriceDomain priceDomain = new PriceDomain("brand1", dateRange, 1, 1, new PriceDomain.Priority(1), new PriceDomain.Money(BigDecimal.TEN, "USD"));

        assertFalse(priceDomain.isApplicable(now.minusDays(2)));
    }

    @Test
    void hasHigherPriorityThan_withHigherPriority_thenReturnTrue() {
        PriceDomain.Priority priority1 = new PriceDomain.Priority(1);
        PriceDomain.Priority priority2 = new PriceDomain.Priority(2);
        PriceDomain priceDomain1 = new PriceDomain("brand1", new PriceDomain.DateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1)), 1, 1, priority1, new PriceDomain.Money(BigDecimal.TEN, "USD"));
        PriceDomain priceDomain2 = new PriceDomain("brand2", new PriceDomain.DateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1)), 2, 2, priority2, new PriceDomain.Money(BigDecimal.TEN, "USD"));

        assertTrue(priceDomain2.hasHigherPriorityThan(priceDomain1));
    }

    @Test
    void hasHigherPriorityThan_withLowerPriority_thenReturnFalse() {
        PriceDomain.Priority priority1 = new PriceDomain.Priority(1);
        PriceDomain.Priority priority2 = new PriceDomain.Priority(2);
        PriceDomain priceDomain1 = new PriceDomain("brand1", new PriceDomain.DateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1)), 1, 1, priority1, new PriceDomain.Money(BigDecimal.TEN, "USD"));
        PriceDomain priceDomain2 = new PriceDomain("brand2", new PriceDomain.DateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1)), 2, 2, priority2, new PriceDomain.Money(BigDecimal.TEN, "USD"));

        assertFalse(priceDomain1.hasHigherPriorityThan(priceDomain2));
    }

    @Test
    void dateRangeValidation_withInvalidDateRange_thenThrowException() {
        LocalDateTime now = LocalDateTime.now();
        assertThrows(DomainException.class, () -> new PriceDomain.DateRange(now.plusDays(1), now));
    }

    @Test
    void priorityValidation_withInvalidPriority_thenThrowException() {
        assertThrows(DomainException.class, () -> new PriceDomain.Priority(-1));
        assertThrows(DomainException.class, () -> new PriceDomain.Priority(null));
    }

    @Test
    void moneyValidation_withInvalidMoney_thenThrowException() {
        assertThrows(DomainException.class, () -> new PriceDomain.Money(BigDecimal.valueOf(-1), "USD"));
        assertThrows(DomainException.class, () -> new PriceDomain.Money(BigDecimal.TEN, "INVALID"));
        assertThrows(DomainException.class, () -> new PriceDomain.Money(new BigDecimal("10.123"), "USD"));
    }
}
