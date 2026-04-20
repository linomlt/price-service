package io.github.linomlt.priceservice.adapter.out.persistence;

import io.github.linomlt.priceservice.application.domain.model.DateRange;
import io.github.linomlt.priceservice.application.domain.model.Money;
import io.github.linomlt.priceservice.application.domain.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {

    Price toDomainEntity(PriceJpaEntity priceJpaEntity) {
        Money money = new Money(priceJpaEntity.getPrice(), priceJpaEntity.getCurrency());
        DateRange validity = new DateRange(priceJpaEntity.getStartDate(), priceJpaEntity.getEndDate());

        return new Price(
                priceJpaEntity.getBrandId(),
                priceJpaEntity.getProductId(),
                money,
                priceJpaEntity.getPriority(),
                priceJpaEntity.getPriceList(),
                validity);
    }
}
