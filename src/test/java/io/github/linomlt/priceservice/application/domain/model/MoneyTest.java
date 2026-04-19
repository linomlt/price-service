package io.github.linomlt.priceservice.application.domain.model;

import io.github.linomlt.priceservice.application.domain.exception.NegativeAmountException;
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
    void shouldThrowExceptionWhenAmountIsNegative() {
        BigDecimal negativeAmount = new BigDecimal("-5.00");

        assertThatExceptionOfType(NegativeAmountException.class)
                .isThrownBy(() -> new Money(negativeAmount, "EUR"))
                .satisfies(ex -> assertThat(ex.getAmount()).isEqualByComparingTo(negativeAmount));
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
