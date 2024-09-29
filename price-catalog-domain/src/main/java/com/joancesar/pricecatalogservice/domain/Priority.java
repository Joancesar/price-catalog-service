package com.joancesar.pricecatalogservice.domain;

import com.joancesar.pricecatalogservice.exceptions.DomainException;

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
