package io.github.linomlt.priceservice.domain.exception;

import java.time.LocalDateTime;

public final class InvalidDateRangeException extends DomainException {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public InvalidDateRangeException(LocalDateTime start, LocalDateTime end) {
        super("Start date '%s' cannot be after end date '%s'".formatted(start, end));
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

}
