package io.github.linomlt.priceservice.adapter.out.persistence;

import io.github.linomlt.priceservice.application.domain.model.Price;
import io.github.linomlt.priceservice.application.port.out.FindApplicablePricesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements FindApplicablePricesPort {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public Optional<Price> findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceRepository.findFirstApplicablePrice(brandId, productId, applicationDate)
                .map(priceMapper::toDomainEntity);
    }

}
