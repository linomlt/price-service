package io.github.linomlt.priceservice.application.port.in;

import io.github.linomlt.priceservice.application.domain.model.Price;

@FunctionalInterface
public interface GetApplicablePriceUseCase {

    Price execute(PriceQuery priceQuery);

}
