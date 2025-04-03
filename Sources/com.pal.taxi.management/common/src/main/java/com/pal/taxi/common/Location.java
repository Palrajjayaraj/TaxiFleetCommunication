package com.pal.taxi.common;

import com.pal.taxi.common.validation.ValidationStatus;

/**
 * Represents the geographical location.<br>
 * This record holds latitude, longitude, and a human-readable name
 *
 * @author Palraj
 */
public record Location(int id, double latitude, double longitude, String readableName) {

	/**
	 * @return validates and verifies that the given lattitude and longitude are
	 *         valid and represents a location.
	 */
	public ValidationStatus validate() {
		// feature for location validation, currently, doing nothing, all locations are
		// considered valid.
		return ValidationStatus.OK_STATUS;
	}

}