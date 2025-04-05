package com.pal.taxi.common.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.pal.taxi.common.Location;
import com.pal.taxi.common.validation.ValidationStatus;

/***
 * Unit test for class {@code Location}
 */
public class LocationTest {

	@Test
	public void testValidLocation() {
		Location validLocation = new Location(1, 12.9715987, 77.594566, "Top Of thw world.");
		ValidationStatus status = validLocation.validate();
		assertTrue(status.isOk());
	}

	@Test
	public void testInvalidLatitudeLocation() {
		Location invalidLatitudeLocation = new Location(2, 100.0, 77.594566, "Invalid Latitude");
		ValidationStatus status = invalidLatitudeLocation.validate();
		assertFalse(status.isOk());
		assertEquals("Latitude should be within range.", status.message());
	}

	@Test
	public void testInvalidLongitudeLocation() {
		Location invalidLongitudeLocation = new Location(3, 12.9715987, 200.0, "Invalid longitude");
		ValidationStatus status = invalidLongitudeLocation.validate();
		assertFalse(status.isOk());
		assertEquals("Longitute should be within range.", status.message());
	}
}
