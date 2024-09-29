package com.joancesar.pricecatalogservice.domain;

import com.joancesar.pricecatalogservice.exceptions.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;

public record PriceDomain(
        String brandId,
        DateRange dateRange,
        Integer priceList,
        Integer productId,
        Priority priority,
        Money price) {

    public boolean isApplicable(LocalDateTime date) {
        return dateRange.contains(date);
    }

    public boolean hasHigherPriorityThan(PriceDomain other) {
        return this.priority.isHigherThan(other.priority);
    }

    record DateRange(LocalDateTime startDate, LocalDateTime endDate) {
        public DateRange {
            Objects.requireNonNull(startDate, "startDate no puede ser nulo");
            Objects.requireNonNull(endDate, "endDate no puede ser nulo");
            if (startDate.isAfter(endDate)) {
                throw new DomainException("startDate debe ser antes o igual a endDate");
            }
        }

        public boolean contains(LocalDateTime date) {
            return !date.isBefore(startDate) && !date.isAfter(endDate);
        }
    }


    record Priority(Integer value) {
        public Priority {
            if (value == null || value < 0) {
                throw new DomainException("Prioidad no puede ser nula o negativa");
            }
        }

        public boolean isHigherThan(Priority other) {
            return this.value > other.value;
        }
    }

    public record Money(
            BigDecimal amount,
            String currency) {
        public Money {
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new DomainException("Amount no puede ser negativo");
            }
            validateCurrencyCode(currency);
            validateAmountPrecision(amount, currency);
        }
        private static void validateCurrencyCode(String currencyCode) {
            try {
                Currency.getInstance(currencyCode);
            } catch (IllegalArgumentException e) {
                throw new DomainException("Currency code invalido: " + currencyCode);
            }
        }

        private static void validateAmountPrecision(BigDecimal amount, String currencyCode) {
            Currency currency = Currency.getInstance(currencyCode);
            int fractionDigits = currency.getDefaultFractionDigits();
            BigDecimal scaledAmount = amount.setScale(fractionDigits, RoundingMode.HALF_UP);
            if (scaledAmount.compareTo(amount) != 0) {
                throw new DomainException(String.format(
                        "La cantidad %s excede la precisión permitida para la moneda %s. Se permiten hasta %d decimales",
                        amount, currencyCode, fractionDigits
                ));
            }
        }
    }
}
