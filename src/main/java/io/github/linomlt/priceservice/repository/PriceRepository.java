package io.github.linomlt.priceservice.repository;

import io.github.linomlt.priceservice.domain.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {

    Optional<Price> findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Long brandId,
            Long productId,
            LocalDateTime applicationDate1,
            LocalDateTime applicationDate2
    );

}
