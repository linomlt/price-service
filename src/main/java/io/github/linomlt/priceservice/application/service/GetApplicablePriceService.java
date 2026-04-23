package io.github.linomlt.priceservice.application.service;

import io.github.linomlt.priceservice.application.exception.PriceNotFoundException;
import io.github.linomlt.priceservice.domain.model.Price;
import io.github.linomlt.priceservice.application.port.in.GetApplicablePriceUseCase;
import io.github.linomlt.priceservice.application.port.in.PriceQuery;
import io.github.linomlt.priceservice.application.port.out.FindApplicablePricesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetApplicablePriceService implements GetApplicablePriceUseCase {

    private final FindApplicablePricesPort findApplicablePricesPort;

    @Override
    public Price execute(PriceQuery priceQuery) {
        List<Price> applicablePrices = findApplicablePricesPort.findAllApplicablePrices(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate());

        return selectHighestPriorityPrice(applicablePrices);
    }

    private Price selectHighestPriorityPrice(List<Price> applicablePrices) {
        return applicablePrices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException("No applicable price found for the given query"));
    }

}
