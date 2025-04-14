package com.pal.taxi;

import java.util.UUID;

import com.pal.taxi.common.ITaxiInfo;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.common.validation.ValidationException;

import lombok.NonNull;

public final class Taxi implements ITaxiInfo {

	public enum TaxiStatus {
		AVAILABLE, BOOKED, RIDING, OFFLINE;
	}

	enum TaxiResponse {
		ACCEPTED, REJECTED;
	}

	private final UUID id;

	private final String numberPlate;

	private final TaxiState state;

	public Taxi(@NonNull UUID id, @NonNull String numberPlate, @NonNull TaxiStatus currentStatus,
			@NonNull Location currentLocation) throws ValidationException {
		this.id = id;
		this.numberPlate = numberPlate;
		this.state = new TaxiState(currentStatus, currentLocation);
		// delegate the validation to state.
	}

	public UUID getId() {
		return id;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public TaxiStatus getStatus() {
		return this.state.getStatus();
	}

	public void updateStatus(TaxiStatus newStatus) throws ValidationException {
		this.state.setStatus(newStatus);
	}

	public Location getCurrentLocation() {
		return this.state.getLocation();
	}

	public BookingResponse respondToBooking(BookingRequest request) {
		if (!TaxiStatus.AVAILABLE.equals(getStatus())) {
			// in an off chance, where the booking request is sent to a taxi,
			// handle gracefully, just decline it.
			return new BookingResponse(request.getUuid(), id, TaxiResponse.REJECTED);
		}
		// display the notification and get it from user.
		return new BookingResponse(request.getUuid(), id, TaxiResponse.REJECTED);
	}
}
