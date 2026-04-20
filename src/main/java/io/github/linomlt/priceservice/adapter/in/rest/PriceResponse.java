package io.github.linomlt.priceservice.adapter.in.rest;

import io.github.linomlt.priceservice.application.domain.model.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        Long brandId,
        Long productId,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {

    public static PriceResponse from(Price price) {
        return new PriceResponse(
                price.getBrandId(),
                price.getProductId(),
                price.getPriceList(),
                price.getValidity().start(),
                price.getValidity().end(),
                price.getMoney().amount(),
                price.getMoney().currency()
        );
    }

}
