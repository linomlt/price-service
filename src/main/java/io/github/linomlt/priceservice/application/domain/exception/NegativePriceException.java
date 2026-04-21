package io.github.linomlt.priceservice.application.domain.exception;

import io.github.linomlt.priceservice.application.domain.model.Money;

public final class NegativePriceException extends DomainException {

    public NegativePriceException(Money money) {
        super("Price cannot be negative: %s %s".formatted(money.amount(), money.currency()));
    }

}
