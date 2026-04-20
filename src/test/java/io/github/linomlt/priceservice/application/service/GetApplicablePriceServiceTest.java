package io.github.linomlt.priceservice.application.service;

import io.github.linomlt.priceservice.application.domain.exception.PriceNotFoundException;
import io.github.linomlt.priceservice.application.domain.model.DateRange;
import io.github.linomlt.priceservice.application.domain.model.Money;
import io.github.linomlt.priceservice.application.domain.model.Price;
import io.github.linomlt.priceservice.application.port.in.PriceQuery;
import io.github.linomlt.priceservice.application.port.out.FindApplicablePricesPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicablePriceServiceTest {

    @Mock
    private FindApplicablePricesPort findApplicablePricesPort;

    @InjectMocks
    private GetApplicablePriceService service;

    @Test
    void shouldReturnPriceWhenPriceIsFound() {
        PriceQuery priceQuery = givenPriceQuery();
        Price expectedPrice = givenExpectedPrice();

        when(findApplicablePricesPort.findApplicablePrice(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate()
        )).thenReturn(Optional.of(expectedPrice));

        Price result = service.execute(priceQuery);

        assertThat(result.getBrandId()).isEqualTo(expectedPrice.getBrandId());
        assertThat(result.getProductId()).isEqualTo(expectedPrice.getProductId());
        assertThat(result.getMoney()).isEqualTo(expectedPrice.getMoney());
        assertThat(result.getPriority()).isEqualTo(expectedPrice.getPriority());
        assertThat(result.getPriceList()).isEqualTo(expectedPrice.getPriceList());
        assertThat(result.getValidity()).isEqualTo(expectedPrice.getValidity());

        verify(findApplicablePricesPort).findApplicablePrice(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate()
        );
    }

    @Test
    void shouldThrowExceptionWhenPriceIsMissing() {
        PriceQuery priceQuery = givenPriceQuery();

        when(findApplicablePricesPort.findApplicablePrice(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate()
        )).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.execute(priceQuery))
                .isInstanceOf(PriceNotFoundException.class);

        verify(findApplicablePricesPort).findApplicablePrice(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate()
        );
    }

    private static PriceQuery givenPriceQuery() {
        return new PriceQuery(
                1L,
                35455L,
                LocalDateTime.of(2026, 6, 14, 10, 0)
        );
    }

    private static Price givenExpectedPrice() {
        Money money = new Money(new BigDecimal("35.50"), "EUR");
        DateRange dateRange = new DateRange(
                LocalDateTime.of(2026, 1, 1, 0, 0, 0),
                LocalDateTime.of(2026, 12, 31, 23, 59, 59)
        );

        return new Price(
                1L,
                35455L,
                money,
                1,
                1,
                dateRange
        );
    }

}