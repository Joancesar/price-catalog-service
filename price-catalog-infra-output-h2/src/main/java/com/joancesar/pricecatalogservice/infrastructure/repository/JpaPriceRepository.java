package com.joancesar.pricecatalogservice.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joancesar.pricecatalogservice.infrastructure.entity.Price;
import com.joancesar.pricecatalogservice.infrastructure.projection.ApplicablePriceProjection;

@Repository
public interface JpaPriceRepository extends JpaRepository<Price, Long> {

    @Query(value = "SELECT p.product_id AS productId, p.brand_id AS brandId, "
            + "p.start_date AS startDate, p.end_date AS endDate, p.price AS price, "
            + "p.price_list AS priceList "
            + "FROM prices p "
            + "WHERE p.product_id = :productId "
            + "AND p.brand_id = :brandId "
            + "AND :applicationDate BETWEEN p.start_date AND p.end_date "
            + "ORDER BY p.priority DESC LIMIT 1",
            nativeQuery = true)
    Optional<ApplicablePriceProjection> findApplicablePrice(@Param("productId") Long productId,
            @Param("brandId") Long brandId,
            @Param("applicationDate") LocalDateTime applicationDate);
}
