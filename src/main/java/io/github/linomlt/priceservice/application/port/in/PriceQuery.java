package io.github.linomlt.priceservice.application.port.in;

import java.time.LocalDateTime;
import java.util.Objects;

public record PriceQuery(
        Long brandId,
        Long productId,
        LocalDateTime applicationDate
) {

    public PriceQuery {
        Objects.requireNonNull(brandId, "Brand ID is mandatory");
        Objects.requireNonNull(productId, "Product ID is mandatory");
        Objects.requireNonNull(applicationDate, "Application date is mandatory");
    }

}
