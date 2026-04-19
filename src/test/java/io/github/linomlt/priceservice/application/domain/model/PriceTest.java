package io.github.linomlt.priceservice.application.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriceTest {

    @Test
    void shouldCreatePrice() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        Money money = new Money(new BigDecimal("35.50"), "EUR");
        Integer priority = 1;
        Integer priceList = 1;
        DateRange validity = new DateRange(
                LocalDateTime.of(2026, 1, 1, 0, 0, 0),
                LocalDateTime.of(2026, 12, 31, 23, 59, 59)
        );

        // When
        Price price = new Price(brandId, productId, money, priority, priceList, validity);

        // Then
        assertThat(price).isNotNull();
        assertThat(price.getBrandId()).isEqualTo(brandId);
        assertThat(price.getProductId()).isEqualTo(productId);
        assertThat(price.getMoney()).isEqualTo(money);
        assertThat(price.getPriority()).isEqualTo(priority);
        assertThat(price.getPriceList()).isEqualTo(priceList);
        assertThat(price.getValidity()).isEqualTo(validity);

    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        // Given
        Long brandId = 1L;
        Long productId = null;
        Money money = new Money(new BigDecimal("35.50"), "EUR");
        Integer priority = 1;
        Integer priceList = 1;
        DateRange validity = new DateRange(
                LocalDateTime.of(2026, 1, 1, 0, 0, 0),
                LocalDateTime.of(2026, 12, 31, 23, 59, 59)
        );

        // When & Then
        assertThatThrownBy(() -> new Price(brandId, productId, money, priority, priceList, validity))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product ID cannot be null");
    }

    @Test
    void shouldReturnTrueWhenDateIsWithinValidRange() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        Money money = new Money(new BigDecimal("35.50"), "EUR");
        Integer priority = 1;
        Integer priceList = 1;
        DateRange validity = new DateRange(
                LocalDateTime.of(2026, 1, 1, 0, 0, 0),
                LocalDateTime.of(2026, 12, 31, 23, 59, 59)
        );
        Price price = new Price(brandId, productId, money, priority, priceList, validity);
        LocalDateTime dateWithinRange = LocalDateTime.of(2026, 9, 15, 12, 0, 0);

        // When
        boolean result = price.isApplicable(dateWithinRange);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDateIsOutsideValidRange() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        Money money = new Money(new BigDecimal("35.50"), "EUR");
        Integer priority = 1;
        Integer priceList = 1;
        DateRange validity = new DateRange(
                LocalDateTime.of(2026, 1, 1, 0, 0, 0),
                LocalDateTime.of(2026, 12, 31, 23, 59, 59)
        );
        Price price = new Price(brandId, productId, money, priority, priceList, validity);
        LocalDateTime dateOutsideRange = LocalDateTime.of(2030, 1, 1, 0, 0, 0);

        // When
        boolean result = price.isApplicable(dateOutsideRange);

        // Then
        assertThat(result).isFalse();
    }

}
