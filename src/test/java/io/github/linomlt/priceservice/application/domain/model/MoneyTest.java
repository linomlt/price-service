package io.github.linomlt.priceservice.application.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class MoneyTest {

    @Test
    void shouldCreateMoneyWhenAmountIsPositive() {
        BigDecimal amount = new BigDecimal("100.00");
        Money money = new Money(amount, "EUR");

        assertThat(money.amount()).isEqualByComparingTo(amount);
        assertThat(money.currency()).isEqualTo("EUR");
    }

    @Test
    void shouldCreateMoneyWhenAmountIsZero() {
        BigDecimal amount = new BigDecimal("0.00");
        Money money = new Money(amount, "EUR");

        assertThat(money.amount()).isEqualByComparingTo(amount);
        assertThat(money.currency()).isEqualTo("EUR");
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Money(null, "EUR"));
    }

    @Test
    void shouldThrowExceptionWhenCurrencyIsNull() {
        BigDecimal amount = new BigDecimal("100.00");

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Money(amount, null));
    }

}
