package com.joancesar.pricecatalogservice.infrastructure.exceptions;

public class InvalidFormatException extends RuntimeException {

    public InvalidFormatException(String message) {
        super(message);
    }
}
