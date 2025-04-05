package com.pal.taxi.common.booking;

import java.time.LocalDateTime;
import java.util.UUID;

import com.pal.taxi.common.TaxiFleetException;

import lombok.NonNull;

public class Ride {

	private final UUID id;

	private final Booking booking;

	private final LocalDateTime startTime;

	private LocalDateTime endTime;

	/**
	 * Should be createable by only the Booking class.
	 */
	/* Package */ static Ride createRideRide(@NonNull Booking booking, @NonNull LocalDateTime startTime)
			throws TaxiFleetException {
		assertInputArguments(booking, startTime);
		return new Ride(booking, startTime);
	}

	private static void assertInputArguments(Booking booking, LocalDateTime startTime) throws TaxiFleetException {
		// nothing as of now to assert
	}

	private Ride(Booking booking, LocalDateTime startTime) {
		this.id = UUID.randomUUID();
		this.booking = booking;
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public UUID getId() {
		return id;
	}

	public Booking getBooking() {
		return booking;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	/* Package */ void closeRide() {
		this.endTime = LocalDateTime.now();
	}

}
