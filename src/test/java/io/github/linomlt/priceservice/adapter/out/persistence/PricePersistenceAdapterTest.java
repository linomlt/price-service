package io.github.linomlt.priceservice.adapter.out.persistence;

import io.github.linomlt.priceservice.application.domain.model.DateRange;
import io.github.linomlt.priceservice.application.domain.model.Money;
import io.github.linomlt.priceservice.application.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({PricePersistenceAdapter.class, PriceMapper.class})
@Sql("/PricePersistenceAdapterTest.sql")
class PricePersistenceAdapterTest {

    @Autowired
    private PricePersistenceAdapter pricePersistenceAdapter;

    @Test
    void shouldReturnBasePrice_WhenItIsTheOnlyAvailablePrice() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 8, 0);
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(1L, 1L, date);

        Price expected = createPrice(1L, 1L, 1, "2024-01-01T00:00:00", "2024-12-31T23:59:59", 0, "100.00", "EUR");
        assertTrue(result.isPresent());
        assertPriceEquals(expected, result.get());
    }

    @Test
    void shouldReturnHigherPriorityPrice_WhenMultiplePricesOverlap() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 11, 0);
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(1L, 1L, date);

        Price expected = createPrice(1L, 1L, 5, "2024-01-01T11:00:00", "2024-01-01T11:30:00", 2, "75.00", "EUR");
        assertTrue(result.isPresent());
        assertPriceEquals(expected, result.get());
    }

    @Test
    void shouldReturnBasePrice_WhenHigherPriorityPricePeriodHasEnded() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 13, 0);
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(1L, 1L, date);

        Price expected = createPrice(1L, 1L, 1, "2024-01-01T00:00:00", "2024-12-31T23:59:59", 0, "100.00", "EUR");
        assertTrue(result.isPresent());
        assertPriceEquals(expected, result.get());
    }

    @Test
    void shouldReturnEmpty_WhenNoPriceIsApplicable() {
        LocalDateTime date = LocalDateTime.of(2023, 12, 31, 23, 59);
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(1L, 1L, date);
        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnEmpty_WhenBrandIdDoesNotExist() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 10, 0);
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(999L, 1L, date);
        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnEmpty_WhenProductIdDoesNotExist() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 10, 0);
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(1L, 999L, date);
        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnPrice_WhenBrandIdIsNotOne() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 10, 0);
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(2L, 1L, date);

        Price expected = createPrice(2L, 1L, 9, "2024-01-01T00:00:00", "2024-12-31T23:59:59", 0, "150.00", "EUR");
        assertTrue(result.isPresent());
        assertPriceEquals(expected, result.get());
    }

    @Test
    void shouldReturnPrice_WhenCurrencyIsUsd() {
        LocalDateTime date = LocalDateTime.of(2024, 10, 15, 12, 0);
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(1L, 1L, date);

        Price expected = createPrice(1L, 1L, 11, "2024-10-01T00:00:00", "2024-10-31T23:59:59", 5, "95.00", "USD");
        assertTrue(result.isPresent());
        assertPriceEquals(expected, result.get());
    }

    @Test
    void shouldReturnPriceWithHigherId_WhenPrioritiesAreEqual() {
        LocalDateTime date = LocalDateTime.of(2024, 7, 15, 12, 0);
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(1L, 1L, date);

        // ID 7: Priority 5, PriceList 7, Price 50.00 EUR
        // ID 8: Priority 5, PriceList 8, Price 60.00 EUR
        // Should select ID 8 as it has the higher ID when priorities are equal
        Price expected = createPrice(1L, 1L, 8, "2024-07-01T00:00:00", "2024-07-31T23:59:59", 5, "60.00", "EUR");
        assertTrue(result.isPresent());
        assertPriceEquals(expected, result.get());
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

    private void assertPriceEquals(Price expected, Price actual) {
        assertAll("Price fields",
                () -> assertEquals(expected.getBrandId(), actual.getBrandId()),
                () -> assertEquals(expected.getProductId(), actual.getProductId()),
                () -> assertEquals(expected.getPriceList(), actual.getPriceList()),
                () -> assertEquals(expected.getPriority(), actual.getPriority()),
                () -> assertEquals(expected.getMoney(), actual.getMoney()),
                () -> assertEquals(expected.getValidity(), actual.getValidity())
        );
    }

}