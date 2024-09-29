package com.joancesar.pricecatalogservice.domain;

import com.joancesar.pricecatalogservice.exceptions.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

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