package com.pal.taxi.web.internal.payload;

import java.time.LocalDate;

public record BookingRequestsConversion(LocalDate date, int requestCount, int bookingCount) {
}