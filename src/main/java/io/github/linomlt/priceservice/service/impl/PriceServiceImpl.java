package io.github.linomlt.priceservice.service.impl;

import io.github.linomlt.priceservice.dto.PriceResponse;
import io.github.linomlt.priceservice.repository.PriceRepository;
import io.github.linomlt.priceservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    @Override
    public Optional<PriceResponse> getEffectivePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceRepository.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, applicationDate, applicationDate)
                .map(p -> new PriceResponse(
                        p.getBrandId(),
                        p.getProductId(),
                        p.getPriceList(),
                        p.getStartDate(),
                        p.getEndDate(),
                        p.getPrice(),
                        p.getCurrency()));
    }

}
