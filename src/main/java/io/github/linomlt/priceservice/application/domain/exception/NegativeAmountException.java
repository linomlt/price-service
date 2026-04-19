package io.github.linomlt.priceservice.application.domain.exception;

import java.math.BigDecimal;

public final class NegativeAmountException extends DomainException {

    private final BigDecimal amount;

    public NegativeAmountException(BigDecimal amount) {
        super("Amount cannot be negative: " + amount);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
