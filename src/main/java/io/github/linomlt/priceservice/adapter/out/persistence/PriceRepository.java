package io.github.linomlt.priceservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<PriceJpaEntity, Long> {

    @Query("""
             SELECT p
             FROM PriceJpaEntity p
             WHERE p.brandId = :brandId
               AND p.productId = :productId
               AND p.startDate <= :applicationDate
               AND p.endDate >= :applicationDate
             ORDER BY p.priority DESC, p.id DESC
             LIMIT 1
            """)
    Optional<PriceJpaEntity> findFirstApplicablePrice(
            @Param("brandId") Long brandId,
            @Param("productId") Long productId,
            @Param("applicationDate") LocalDateTime applicationDate
    );

}
