package io.github.linomlt.priceservice.application.port.out;

import io.github.linomlt.priceservice.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface FindApplicablePricesPort {

    List<Price> findAllApplicablePrices(Long brandId, Long productId, LocalDateTime applicationDate);

}