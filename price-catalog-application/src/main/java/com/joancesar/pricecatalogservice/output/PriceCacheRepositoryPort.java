package com.joancesar.pricecatalogservice.output;

import java.util.Optional;

public interface PriceCacheRepositoryPort {

    <T> Optional<T> get(String cacheName, Object key, Class<T> type);

    <T> void put(String cacheName, Object key, T value);
}
