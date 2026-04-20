package io.github.linomlt.priceservice.application.service;

import io.github.linomlt.priceservice.application.domain.exception.PriceNotFoundException;
import io.github.linomlt.priceservice.application.domain.model.Price;
import io.github.linomlt.priceservice.application.port.in.GetApplicablePriceUseCase;
import io.github.linomlt.priceservice.application.port.in.PriceQuery;
import io.github.linomlt.priceservice.application.port.out.FindApplicablePricesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetApplicablePriceService implements GetApplicablePriceUseCase {

    private final FindApplicablePricesPort findApplicablePricesPort;

    @Override
    public Price execute(PriceQuery priceQuery) {
        return findApplicablePricesPort.findApplicablePrice(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate()
        ).orElseThrow(() -> new PriceNotFoundException("No applicable price found for the given query"));
    }

}
