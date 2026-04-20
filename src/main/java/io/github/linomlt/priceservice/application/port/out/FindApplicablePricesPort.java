package io.github.linomlt.priceservice.application.port.out;

import io.github.linomlt.priceservice.application.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FindApplicablePricesPort {

    Optional<Price> findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate);

}