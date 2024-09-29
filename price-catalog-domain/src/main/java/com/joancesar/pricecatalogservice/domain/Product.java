package com.joancesar.pricecatalogservice.domain;

import com.joancesar.pricecatalogservice.exceptions.DomainException;

public record Product(
        Integer id
) {

    public Product {
        if (id == null) {
            throw new DomainException("Id no puede ser nulo");
        }
    }
}
