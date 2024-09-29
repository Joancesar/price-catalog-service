package com.joancesar.pricecatalogservice.domain;

import com.joancesar.pricecatalogservice.exceptions.DomainException;

public record Brand(
        Integer id
) {

    public Brand {
        if (id == null) {
            throw new DomainException("Id no puede ser nulo");
        }
    }
}
