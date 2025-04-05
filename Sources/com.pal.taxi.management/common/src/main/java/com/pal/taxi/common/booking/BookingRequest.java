package com.pal.taxi.common.booking;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.lock.LockRunner;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.common.validation.ValidationStatus;

/**
 * Represents a booking request from the user.
 * 
 * @author Palraj
 */
public class BookingRequest {

	/**
	 * Represents the possible statuses of a booking request.
	 */
	public enum Status {

		/** The booking request is pending and awaiting approval. */
		PENDING,

		/** The booking request has been rejected. */
		REJECTED,

		/** A taxi has been assigned to fulfill the booking request. */
		ASSIGNED_TAXI;
	}

	private final String uuid;

	private final String userId;

	private final LocalDateTime requestTime;

	private final Location pickupLocation;

	private final Location dropoffLocation;

	private Status status;

	/** Runner to prtcted status information */
	private final LockRunner statusLockRunner = new LockRunner(new ReentrantReadWriteLock());

	private BookingRequest(String userId, LocalDateTime requestTime, Location pickupLocation,
			Location dropoffLocation) {
		this.uuid = UUID.randomUUID().toString();
		this.status = Status.PENDING;
		this.userId = userId;
		this.requestTime = requestTime;
		this.pickupLocation = pickupLocation;
		this.dropoffLocation = dropoffLocation;
	}

	public static BookingRequest createRequest(String userId, LocalDateTime bookingTime, Location pickupLocation,
			Location dropoffLocation) throws TaxiFleetException {
		assertInputArguments(userId, bookingTime, pickupLocation, dropoffLocation);
		return new BookingRequest(userId, bookingTime, pickupLocation, dropoffLocation);
	}

	private static void assertInputArguments(String userId, LocalDateTime bookingTime, Location pickupLocation,
			Location dropoffLocation) throws TaxiFleetException {
		Objects.requireNonNull(userId);
		Objects.requireNonNull(bookingTime);
		Objects.requireNonNull(pickupLocation);
		Objects.requireNonNull(dropoffLocation);
		assertLocation(pickupLocation);
		assertLocation(dropoffLocation);
		if (pickupLocation.equals(dropoffLocation)) {
			throw new ValidationException("The pickup location and drop off location cannot be same.", null);
		}
	}

	private static void assertLocation(Location location) throws TaxiFleetException {
		ValidationStatus result = location.validate();
		if (!result.isOk()) {
			throw new ValidationException(result.message(), result.throwable());
		}
	}

	public String getUuid() {
		return uuid;
	}

	public String getUserId() {
		return userId;
	}

	public LocalDateTime getRequestTime() {
		return requestTime;
	}

	public Location getPickupLocation() {
		return pickupLocation;
	}

	public Location getDropoffLocation() {
		return dropoffLocation;
	}

	public Status getStatus() {
		return statusLockRunner.runWithReadLock(() -> status);
	}

	/**
	 * Updates the status of the booking request.
	 *
	 * @param status The new status to set.
	 * @throws TaxiFleetException If the status is null or if the request is already
	 *                            closed.
	 */
	public void updateStatus(Status status) throws TaxiFleetException {
		statusLockRunner.runWithWriteLock(() -> {
			if (null == status) {
				throw new TaxiFleetException("Status cannot be set to null. provide valid status");
			}
			if (Status.ASSIGNED_TAXI.equals(getStatus()) || Status.REJECTED.equals(getStatus())) {
				throw new TaxiFleetException("The request is already closed and cannot set any more status.");
			}
			this.status = status;
		});
	}

	@Override
	public String toString() {
		return "BookingRequest{" + "requestId= " + uuid + ", user=" + userId + ", bookingDate=" + requestTime
				+ ", currentStatus=" + status + '}';
	}

}
