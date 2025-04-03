package com.pal.taxi.common.booking;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ride {

	private final UUID id;

	private final Booking booking;

	private final LocalDateTime startTime;

	private LocalDateTime endTime;

	public Ride(Booking booking, LocalDateTime startTime) {
		this.id = UUID.randomUUID();
		this.booking = booking;
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
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

}
