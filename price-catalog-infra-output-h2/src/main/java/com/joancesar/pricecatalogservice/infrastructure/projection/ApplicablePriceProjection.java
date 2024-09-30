package com.joancesar.pricecatalogservice.infrastructure.projection;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ApplicablePriceProjection {
    String getProductId();
    String getBrandId();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    BigDecimal getPrice();
    Integer getPriceList();
}
