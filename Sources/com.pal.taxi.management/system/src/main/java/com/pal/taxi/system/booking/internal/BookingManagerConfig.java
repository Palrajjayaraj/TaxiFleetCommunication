package com.pal.taxi.system.booking.internal;

/** various configuration for the booking requests manager to run */
public record BookingManagerConfig(int requestTimeoutMillis, int perBatchTimeoutMillis, int maxTaxisPerBatch) {

	private static final int TIMEOUT_FOR_BOOKING_REQUEST_EXPIRY = 60_000;

	private static final int TIMEOUT_FOR_BOOKING_REQUEST_EXPIRY_PER_BATCH = 30_000;

	private static final int MAX_TAXIS_PER_BATCH = 3;

	public static BookingManagerConfig defaults() {
		return new BookingManagerConfig(TIMEOUT_FOR_BOOKING_REQUEST_EXPIRY,
				TIMEOUT_FOR_BOOKING_REQUEST_EXPIRY_PER_BATCH, MAX_TAXIS_PER_BATCH);
	}
}
