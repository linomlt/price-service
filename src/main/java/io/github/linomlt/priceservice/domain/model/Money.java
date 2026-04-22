package io.github.linomlt.priceservice.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount, String currency) {

    public Money {
        Objects.requireNonNull(amount, "Amount cannot be null");
        Objects.requireNonNull(currency, "Currency cannot be null");

        amount = amount.setScale(2, RoundingMode.HALF_UP);

    }

    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0 &&
                Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        // stripTrailingZeros ensures that equivalent values with different scales (e.g., 2.0 and 2.00) result in the
        // same hash code, maintaining the equals/hashCode contract.
        return Objects.hash(amount.stripTrailingZeros(), currency);
    }

}
