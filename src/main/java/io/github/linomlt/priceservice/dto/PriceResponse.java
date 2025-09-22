package io.github.linomlt.priceservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record PriceResponse(Long brandId,
                            Long productId,
                            Long priceList,
                            LocalDateTime startDate,
                            LocalDateTime endDate,
                            BigDecimal price,
                            Currency currency) {
}
