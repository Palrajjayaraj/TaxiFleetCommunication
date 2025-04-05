package com.pal.taxi.common.booking.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.common.booking.BookingRequest.Status;
import com.pal.taxi.common.validation.ValidationException;

/**
 * Junit for {@code BookingRequest}
 */
public class BookingRequestTest {

	private final Location validPickup = new Location(1, 12.9716, 77.5946, "Prozone Mall");

	private final Location validDropoff = new Location(2, 12.9352, 77.6142, "Bus Terminal");

	private final UUID userID = UUID.randomUUID();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testCreateValidBookingRequest() throws TaxiFleetException {

		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), validPickup, validDropoff);
		assertNotNull(request.getUuid());
		assertEquals(userID, request.getUserId());
		assertEquals(Status.PENDING, request.getStatus()); // initial status
		assertEquals(validPickup, request.getPickupLocation());
		assertEquals(validDropoff, request.getDropoffLocation());
	}

	@Test
	public void testUpdateStatusSuccessfully() throws TaxiFleetException {
		// this would be set by the fleet management
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), validPickup, validDropoff);
		request.updateStatus(Status.ASSIGNED_TAXI);
		assertEquals(Status.ASSIGNED_TAXI, request.getStatus());
	}

	@Test
	public void testUpdateRejectStatusSuccessfully() throws TaxiFleetException {
		// this would be set by the fleet management
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), validPickup, validDropoff);
		request.updateStatus(Status.REJECTED);
		assertEquals(Status.REJECTED, request.getStatus());
	}

	@Test
	public void testNullUserIdThrowsException() throws TaxiFleetException {
		thrown.expect(NullPointerException.class);
		BookingRequest.createRequest(null, LocalDateTime.now(), validPickup, validDropoff);
	}

	@Test
	public void testNullPickupLocationThrowsException() throws TaxiFleetException {
		thrown.expect(NullPointerException.class);
		BookingRequest.createRequest(userID, LocalDateTime.now(), null, validDropoff);
	}

	@Test
	public void testNullDropoffLocationFails() throws TaxiFleetException {
		thrown.expect(NullPointerException.class);
		BookingRequest.createRequest(userID, LocalDateTime.now(), validPickup, null);
	}

	@Test
	public void testInvalidPickupLocationFails() throws TaxiFleetException {
		Location invalidLocation = new Location(3, 100.0, 77.5946, "Invalid Latitude");
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Latitude should be within range.");
		BookingRequest.createRequest(userID, LocalDateTime.now(), invalidLocation, validDropoff);
	}

	@Test
	public void testSamePickupAndDropoffFails() throws TaxiFleetException {
		Location sameLocation = new Location(4, 12.9716, 77.5946, "Same Point");
		thrown.expect(ValidationException.class);
		thrown.expectMessage("The pickup location and drop off location cannot be same.");
		BookingRequest.createRequest(userID, LocalDateTime.now(), sameLocation, sameLocation);
	}

	@Test
	public void testUpdateStatusToNullFails() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), validPickup, validDropoff);
		thrown.expect(TaxiFleetException.class);
		thrown.expectMessage("Status cannot be set to null. provide valid status");
		request.updateStatus(null);
	}

	@Test
	public void testUpdateStatusAfterRejectionFails() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), validPickup, validDropoff);
		request.updateStatus(Status.REJECTED);
		thrown.expect(TaxiFleetException.class);
		thrown.expectMessage("The request is already closed and cannot set any more status.");
		request.updateStatus(Status.ASSIGNED_TAXI);
	}

	@Test
	public void testUpdateStatusAfterAssigningTaxiFails() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), validPickup, validDropoff);
		request.updateStatus(Status.ASSIGNED_TAXI);
		thrown.expect(TaxiFleetException.class);
		thrown.expectMessage("The request is already closed and cannot set any more status.");
		request.updateStatus(Status.REJECTED);
	}
}
