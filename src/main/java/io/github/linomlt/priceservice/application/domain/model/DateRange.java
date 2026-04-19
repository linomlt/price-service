package io.github.linomlt.priceservice.application.domain.model;

import io.github.linomlt.priceservice.application.domain.exception.InvalidDateRangeException;

import java.time.LocalDateTime;
import java.util.Objects;

public record DateRange(LocalDateTime start, LocalDateTime end) {

    public DateRange {
        Objects.requireNonNull(start, "Start date cannot be null");
        Objects.requireNonNull(end, "End date cannot be null");

        if (start.isAfter(end)) {
            throw new InvalidDateRangeException(start, end);
        }
    }

    public boolean isWithin(LocalDateTime date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }

}
