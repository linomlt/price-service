package io.github.linomlt.priceservice.adapter.out.persistence;

import io.github.linomlt.priceservice.domain.model.Price;
import io.github.linomlt.priceservice.application.port.out.FindApplicablePricesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements FindApplicablePricesPort {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public List<Price> findAllApplicablePrices(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceRepository.findAllApplicablePrices(brandId, productId, applicationDate).stream()
                .map(priceMapper::toDomainEntity)
                .toList();
    }

}
