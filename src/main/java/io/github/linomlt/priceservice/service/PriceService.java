package io.github.linomlt.priceservice.service;

import io.github.linomlt.priceservice.dto.PriceResponse;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceService {

    Optional<PriceResponse> getEffectivePrice(Long brandId, Long productId, LocalDateTime applicationDate);

}
