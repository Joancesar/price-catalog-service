package com.joancesar.pricecatalogservice.infrastructure.api;

import com.joancesar.pricecatalogservice.infrastructure.api.api.PriceApiDelegate;
import com.joancesar.pricecatalogservice.infrastructure.api.model.PriceDTO;
import com.joancesar.pricecatalogservice.infrastructure.exceptions.PriceNotFoundException;
import com.joancesar.pricecatalogservice.infrastructure.mapper.PriceDtoMapper;
import com.joancesar.pricecatalogservice.usecase.GetPriceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PriceApiDelegateImpl implements PriceApiDelegate {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceDtoMapper priceDtoMapper;

    public PriceApiDelegateImpl(GetPriceUseCase getPriceUseCase, PriceDtoMapper priceDtoMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceDtoMapper = priceDtoMapper;
    }

    @Override
    public ResponseEntity<PriceDTO> getApplicablePrice(Long brandId, Long productId, String date) {
        return ResponseEntity.ok(
                priceDtoMapper.fromDomain(
                        getPriceUseCase.getPrice(priceDtoMapper.toDomain(brandId, productId, date))
                        .orElseThrow(() -> new PriceNotFoundException("No se encontró precio aplicable"))
                )
        );
    }
}
