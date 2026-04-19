package io.github.linomlt.priceservice.application.domain.model;

import io.github.linomlt.priceservice.application.domain.exception.InvalidDateRangeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class DateRangeTest {

    @Test
    void shouldCreateDateRangeWhenStartIsBeforeEnd() {
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 19, 18, 0);
        DateRange dateRange = new DateRange(start, end);

        assertThat(dateRange.start()).isEqualTo(start);
        assertThat(dateRange.end()).isEqualTo(end);
    }

    @Test
    void shouldCreateDateRangeWhenStartEqualsEnd() {
        LocalDateTime sameDate = LocalDateTime.of(2026, 4, 19, 12, 0);
        DateRange dateRange = new DateRange(sameDate, sameDate);

        assertThat(dateRange.start()).isEqualTo(sameDate);
        assertThat(dateRange.end()).isEqualTo(sameDate);
    }

    @Test
    void shouldThrowExceptionWhenStartIsAfterEnd() {
        LocalDateTime start = LocalDateTime.of(2026, 4, 19, 18, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 10, 0);

        assertThatExceptionOfType(InvalidDateRangeException.class)
                .isThrownBy(() -> new DateRange(start, end))
                .satisfies(ex -> {
                    assertThat(ex.getStart()).isEqualTo(start);
                    assertThat(ex.getEnd()).isEqualTo(end);
                });
    }

    @Test
    void shouldThrowExceptionWhenStartIsNull() {
        LocalDateTime end = LocalDateTime.of(2026, 4, 19, 18, 0);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new DateRange(null, end));
    }

    @Test
    void shouldThrowExceptionWhenEndIsNull() {
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new DateRange(start, null));
    }

    @Test
    void shouldReturnTrueWhenDateIsWithinRange() {
        LocalDateTime start = LocalDateTime.of(2026, 4, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 20, 0, 0);
        LocalDateTime dateInRange = LocalDateTime.of(2026, 4, 15, 0, 0);
        DateRange dateRange = new DateRange(start, end);

        assertThat(dateRange.isWithin(dateInRange)).isTrue();
    }

    @Test
    void shouldReturnTrueWhenDateIsExactlyAtStart() {
        LocalDateTime start = LocalDateTime.of(2026, 4, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 20, 0, 0);
        DateRange dateRange = new DateRange(start, end);

        assertThat(dateRange.isWithin(start)).isTrue();
    }

    @Test
    void shouldReturnTrueWhenDateIsExactlyAtEnd() {
        LocalDateTime start = LocalDateTime.of(2026, 4, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 20, 0, 0);
        DateRange dateRange = new DateRange(start, end);

        assertThat(dateRange.isWithin(end)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDateIsBeforeStart() {
        LocalDateTime start = LocalDateTime.of(2026, 4, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 20, 0, 0);
        LocalDateTime dateBeforeStart = LocalDateTime.of(2026, 4, 9, 0, 0);
        DateRange dateRange = new DateRange(start, end);

        assertThat(dateRange.isWithin(dateBeforeStart)).isFalse();
    }

    @Test
    void shouldReturnFalseWhenDateIsAfterEnd() {
        LocalDateTime start = LocalDateTime.of(2026, 4, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 20, 0, 0);
        LocalDateTime dateAfterEnd = LocalDateTime.of(2026, 4, 21, 0, 0);
        DateRange dateRange = new DateRange(start, end);

        assertThat(dateRange.isWithin(dateAfterEnd)).isFalse();
    }

}