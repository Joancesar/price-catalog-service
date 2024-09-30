package com.joancesar.pricecatalogservice.infrastructure.exceptions;

import com.joancesar.pricecatalogservice.exceptions.DomainException;

public class PriceNotFoundException extends DomainException {
    public PriceNotFoundException(String message) {
        super(message);
    }
}
