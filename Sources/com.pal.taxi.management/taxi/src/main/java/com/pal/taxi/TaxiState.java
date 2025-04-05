package com.pal.taxi;

import java.util.Objects;

import com.pal.taxi.Taxi.TaxiStatus;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.common.validation.ValidationStatus;

import lombok.NonNull;

//the changing state of taxi
/*Package*/ class TaxiState {

	private TaxiStatus status;

	private Location location;

	public TaxiState(TaxiStatus status, Location location) throws ValidationException {
		this.status = (Objects.requireNonNull(status, "status must not be null"));
		this.setLocation(Objects.requireNonNull(location, "location must not be null"));
	}

	public TaxiStatus getStatus() {
		return status;
	}

	public Location getLocation() {
		return location;
	}

	public void setStatus(TaxiStatus status) throws ValidationException {
		TaxiStatusValidator taxiStatusValidator = new TaxiStatusValidator(getStatus(), status);
		ValidationStatus validStatus = taxiStatusValidator.validate();
		if (validStatus.isOk()) {
			this.status = status;
		} else {
			throw new ValidationException(validStatus);
		}
	}

	public void setLocation(@NonNull Location location) throws ValidationException {
		ValidationStatus validStatus = location.validate();
		if (validStatus.isOk()) {
			this.location = location;
		} else {
			throw new ValidationException(validStatus);
		}
	}
}
