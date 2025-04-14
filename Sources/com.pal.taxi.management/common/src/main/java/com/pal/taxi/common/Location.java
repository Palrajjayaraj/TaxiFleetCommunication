package com.pal.taxi.common;

import com.pal.taxi.common.validation.ValidationStatus;

/**
 * Represents the geographical location.<br>
 * This record holds latitude, longitude, and a human-readable name
 *
 * @author Palraj
 */
public record Location(int id, double latitude, double longitude, String readableName) implements Comparable<Location> {

	/**
	 * @return validates and verifies that the given lattitude and longitude are
	 *         valid and represents a location.
	 */
	public ValidationStatus validate() {
		// feature for location validation, currently, doing nothing, all locations are
		// considered valid.
		if (latitude < -90.0 || latitude > 90.0) {
			return ValidationStatus.createErrorStatus("Latitude should be within range.", null);
		}
		if (longitude < -180.0 || longitude > 180.0) {
			return ValidationStatus.createErrorStatus("Longitute should be within range.", null);
		}
		return ValidationStatus.OK_STATUS;
	}

	@Override
	public int compareTo(Location o) {
		return this.readableName.compareTo(o.readableName);
	}

}