package io.github.linomlt.priceservice.application.service;

import io.github.linomlt.priceservice.application.exception.PriceNotFoundException;
import io.github.linomlt.priceservice.domain.model.DateRange;
import io.github.linomlt.priceservice.domain.model.Money;
import io.github.linomlt.priceservice.domain.model.Price;
import io.github.linomlt.priceservice.application.port.in.PriceQuery;
import io.github.linomlt.priceservice.application.port.out.FindApplicablePricesPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

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
    void shouldReturnPriceWhenOnePriceIsFound() {
        PriceQuery priceQuery = createPriceQuery();
        Price expectedPrice = createPrice(0);

        when(findApplicablePricesPort.findAllApplicablePrices(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate()
        )).thenReturn(List.of(expectedPrice));

        Price result = service.execute(priceQuery);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedPrice);

        verify(findApplicablePricesPort).findAllApplicablePrices(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate()
        );
    }

    @Test
    void shouldThrowExceptionWhenNoPriceIsFound() {
        PriceQuery priceQuery = createPriceQuery();

        when(findApplicablePricesPort.findAllApplicablePrices(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate()
        )).thenReturn(List.of());

        assertThatThrownBy(() -> service.execute(priceQuery))
                .isInstanceOf(PriceNotFoundException.class);
    }

    @Test
    void shouldReturnHighestPriorityPriceWhenMultiplePricesAreFound() {
        PriceQuery priceQuery = createPriceQuery();
        Price lowPriorityPrice = createPrice(0);
        Price highPriorityPrice = createPrice(1, 2, new BigDecimal("45.50"));

        when(findApplicablePricesPort.findAllApplicablePrices(
                priceQuery.brandId(),
                priceQuery.productId(),
                priceQuery.applicationDate()
        )).thenReturn(List.of(lowPriorityPrice, highPriorityPrice));

        Price result = service.execute(priceQuery);

        assertThat(result).usingRecursiveComparison().isEqualTo(highPriorityPrice);
    }

    private static PriceQuery createPriceQuery() {
        return new PriceQuery(
                1L,
                35455L,
                LocalDateTime.of(2026, 6, 14, 10, 0)
        );
    }

    private static Price createPrice(int priority) {
        return createPrice(priority, 1, new BigDecimal("35.50"));
    }

    private static Price createPrice(int priority, int priceList, BigDecimal amount) {
        Money money = new Money(amount, "EUR");
        DateRange dateRange = new DateRange(
                LocalDateTime.of(2026, 1, 1, 0, 0, 0),
                LocalDateTime.of(2026, 12, 31, 23, 59, 59)
        );

        return new Price(
                1L,
                35455L,
                money,
                priority,
                priceList,
                dateRange
        );
    }

}