package io.github.linomlt.priceservice.adapter.out.persistence;

import io.github.linomlt.priceservice.domain.model.DateRange;
import io.github.linomlt.priceservice.domain.model.Money;
import io.github.linomlt.priceservice.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({PricePersistenceAdapter.class, PriceMapper.class})
@Sql("/PricePersistenceAdapterTest.sql")
class PricePersistenceAdapterIntegrationTest {

    @Autowired
    private PricePersistenceAdapter pricePersistenceAdapter;

    @Test
    void shouldReturnBasePrice_WhenItIsTheOnlyAvailablePrice() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 8, 0);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(1L, 1L, date);

        Price expected = createPrice(1L, 1L, 1, "2024-01-01T00:00:00", "2024-12-31T23:59:59", 0, "100.00", "USD");
        assertEquals(1, prices.size());
        assertThat(prices.getFirst()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldReturnAllPrices_WhenMultiplePricesOverlap() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 11, 0);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(1L, 1L, date);

        assertEquals(3, prices.size());
        assertTrue(prices.stream().anyMatch(p -> p.getPriceList() == 1));
        assertTrue(prices.stream().anyMatch(p -> p.getPriceList() == 2));
        assertTrue(prices.stream().anyMatch(p -> p.getPriceList() == 5));
    }

    @Test
    void shouldReturnEmpty_WhenNoPriceIsApplicable() {
        LocalDateTime date = LocalDateTime.of(2023, 12, 31, 23, 59);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(1L, 1L, date);
        assertTrue(prices.isEmpty());
    }

    @Test
    void shouldReturnEmpty_WhenBrandIdDoesNotExist() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 10, 0);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(999L, 1L, date);
        assertTrue(prices.isEmpty());
    }

    @Test
    void shouldReturnEmpty_WhenProductIdDoesNotExist() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 10, 0);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(1L, 999L, date);
        assertTrue(prices.isEmpty());
    }

    @Test
    void shouldReturnPrice_WhenBrandIdIsNotOne() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 10, 0);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(2L, 1L, date);

        Price expected = createPrice(2L, 1L, 9, "2024-01-01T00:00:00", "2024-12-31T23:59:59", 0, "150.00", "EUR");
        assertEquals(1, prices.size());
        assertThat(prices.getFirst()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldReturnPrice_WhenDateIsExactlyAtStartDate() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 10, 0, 0);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(1L, 1L, date);

        assertTrue(prices.stream().anyMatch(p -> p.getPriceList() == 2));
    }

    @Test
    void shouldReturnPrice_WhenDateIsExactlyAtEndDate() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(1L, 1L, date);

        assertTrue(prices.stream().anyMatch(p -> p.getPriceList() == 2));
    }

    @Test
    void shouldNotReturnPrice_WhenDateIsOneSecondBeforeStartDate() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 9, 59, 59);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(1L, 1L, date);

        assertFalse(prices.stream().anyMatch(p -> p.getPriceList() == 2));
    }

    @Test
    void shouldNotReturnPrice_WhenDateIsOneSecondAfterEndDate() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 12, 0, 1);
        List<Price> prices = pricePersistenceAdapter.findAllApplicablePrices(1L, 1L, date);

        assertFalse(prices.stream().anyMatch(p -> p.getPriceList() == 2));
    }

    private Price createPrice(Long brandId, Long productId, Integer priceList, String start, String end,
                              Integer priority, String amount, String currency) {
        return new Price(
                brandId,
                productId,
                new Money(new BigDecimal(amount), currency),
                priority,
                priceList,
                new DateRange(LocalDateTime.parse(start), LocalDateTime.parse(end))
        );
    }

}