package com.joancesar.pricecatalogservice.infrastructure.service;

import java.util.Optional;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.joancesar.pricecatalogservice.output.PriceCacheRepositoryPort;

@Component
public class PriceCacheRepositoryAdapter implements PriceCacheRepositoryPort {

    private final CacheManager cacheManager;

    public PriceCacheRepositoryAdapter(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <T> Optional<T> get(String cacheName, Object key, Class<T> type) {
        return Optional.ofNullable(cacheManager.getCache(cacheName))
                .map(cache -> cache.get(key, type));
    }

    @Override
    public <T> void put(String cacheName, Object key, T value) {
        Optional.ofNullable(cacheManager.getCache(cacheName))
                .ifPresent(cache -> cache.put(key, value));
    }
}
