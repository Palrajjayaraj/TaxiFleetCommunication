package com.pal.taxi.common.booking;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.pal.taxi.common.ITaxiInfo;
import com.pal.taxi.common.TaxiFleetException;

/**
 * Represents a booking between the user, fleet management and the taxi.
 * 
 * @author Palraj
 */
public class Booking {

	/**
	 * Represents the possible statuses of a booking.
	 */
	public enum Status {
		/** The booking has been confirmed. */
		CONFIRMED,

		/** Ride is in progress. */
		IN_PROGRESS,

		/** The booking and ride has been completed. */
		COMPLETED,

		/** The ride has been cancelled. */
		CANCELLED;
	}

	private final String uuid;

	private final BookingRequest request;

	private final ITaxiInfo taxi;

	private final LocalDateTime bookConfirmedTime;

	private Status status;

	/**
	 * Should be createable by only the Booking Request class.
	 */
	public static Booking createBooking(BookingRequest request, ITaxiInfo assignedTaxi,
			LocalDateTime bookingConfirmationTime) throws TaxiFleetException {
		assertInputArguments(request, assignedTaxi, bookingConfirmationTime);
		return new Booking(request, assignedTaxi, bookingConfirmationTime);
	}

	private static void assertInputArguments(BookingRequest request, ITaxiInfo assignedTaxi,
			LocalDateTime bookConfirmedTime) {
		Objects.requireNonNull(request);
		Objects.requireNonNull(assignedTaxi);
		Objects.requireNonNull(bookConfirmedTime);
	}

	private Booking(BookingRequest request, ITaxiInfo assignedTaxi, LocalDateTime bookConfirmedTime) {
		this.uuid = UUID.randomUUID().toString();
		this.request = request;
		this.taxi = assignedTaxi;
		this.bookConfirmedTime = bookConfirmedTime;
		this.status = Status.CONFIRMED;
	}

	public String getUuid() {
		return uuid;
	}

	public BookingRequest getRequest() {
		return request;
	}

	public ITaxiInfo getTaxi() {
		return taxi;
	}

	public LocalDateTime getBookConfirmedTime() {
		return bookConfirmedTime;
	}

	public void updateStatus(Status status) throws TaxiFleetException {
		if (null == status) {
			throw new TaxiFleetException("Status cannot be set to null. provide valid status");
		}
		ensureBookingNotClosed();
		this.status = status;
	}

	private void ensureBookingNotClosed() throws TaxiFleetException {
		if (Status.CANCELLED.equals(getStatus()) || Status.COMPLETED.equals(getStatus())) {
			throw new TaxiFleetException("The booking is already closed and cannot set any more status.");
		}
	}

	private void ensureBookingNotInProgress() throws TaxiFleetException {
		if (Status.IN_PROGRESS.equals(getStatus())) {
			throw new TaxiFleetException("The booking is already in-progress.");
		}
	}

	public void startRide() throws TaxiFleetException {
		ensureBookingNotInProgress();
		ensureBookingNotClosed();
		this.status = Status.IN_PROGRESS;
		this.ride = Ride.createRideRide(this, LocalDateTime.now());
	}

	public void completeRide() throws TaxiFleetException {
		ensureBookingNotClosed();
		this.ride.closeRide();
		this.status = Status.COMPLETED;
	}

	public void cancelRide() throws TaxiFleetException {
		ensureBookingNotInProgress();
		ensureBookingNotClosed();
		this.status = Status.CANCELLED;
	}

	public Status getStatus() {
		return status;
	}

	/**
	 * @return Could be {@code null}, if the booking is not yet started or
	 *         cancelled.
	 */
	public Ride getRide() {
		return ride;
	}

	private Ride ride;

}