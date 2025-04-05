package com.pal.taxi;

import com.pal.taxi.Taxi.TaxiStatus;
import com.pal.taxi.common.validation.ValidationStatus;

/**
 * validator that validates the transition from current status to new status.
 * 
 * @author Palraj
 */
public class TaxiStatusValidator {

	private final TaxiStatus currentStatus;

	private final TaxiStatus newStatus;

	public TaxiStatusValidator(TaxiStatus currentStatus, TaxiStatus newStatus) {
		this.currentStatus = currentStatus;
		this.newStatus = newStatus;
	}

	public ValidationStatus validate() {
		switch (currentStatus) {
		case AVAILABLE:
			return validateTransitionFromAvailableState();
		case BOOKED:
			return validateTransitionFromBookedState();
		case OFFLINE:
			return validateTransitionFromOfflineState();
		case RIDING:
			return validateTransitionFromRidingState();
		default:
			return ValidationStatus.createErrorStatus("Unknown new status: " + newStatus);
		}
	}

	private ValidationStatus validateTransitionFromRidingState() {
		// can move to available only from Riding state, rest are not allowed.
		if (TaxiStatus.AVAILABLE.equals(newStatus)) {
			return ValidationStatus.OK_STATUS;
		}
		return ValidationStatus.createErrorStatus(
				"Moving from Riding to available is allowed. It is not allowed to move to any other status.");
	}

	private ValidationStatus validateTransitionFromOfflineState() {
		// can move to available only from offline state, rest are not allowed.
		if (TaxiStatus.AVAILABLE.equals(newStatus) || TaxiStatus.OFFLINE.equals(newStatus)) {
			return ValidationStatus.OK_STATUS;
		}
		return ValidationStatus.createErrorStatus(
				"Moving from offline to available is allowed. It is not allowed to move to any other status.");
	}

	private ValidationStatus validateTransitionFromBookedState() {
		switch (newStatus) {
		case AVAILABLE: // this is okay, the booking is cancelled, moving to available
		case BOOKED:// this is okay
		case RIDING: // starting ride
			return ValidationStatus.OK_STATUS;
		case OFFLINE:
			return ValidationStatus.createErrorStatus("A booking has been assigned, moving to offline is not allowed.");
		default:
			return ValidationStatus.createErrorStatus("Unknown new status: " + newStatus);
		}
	}

	private ValidationStatus validateTransitionFromAvailableState() {
		switch (newStatus) {
		case AVAILABLE: // this is okay.
		case BOOKED:// this is okay
		case OFFLINE: // this is okay
			return ValidationStatus.OK_STATUS;
		case RIDING:
			return ValidationStatus
					.createErrorStatus("Cannot set riding state, from available and must move through Booked.");
		default:
			return ValidationStatus.createErrorStatus("Unknown new status: " + newStatus);
		}
	}

}
