package com.pal.taxi.web.internal.payload;

import java.time.LocalDate;

/**
 * represents a single Booking trend.
 */
public record BookingTrend(LocalDate date, int count) {
}