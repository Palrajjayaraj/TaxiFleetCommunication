package com.pal.taxi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Test;

import com.pal.taxi.Taxi.TaxiResponse;
import com.pal.taxi.Taxi.TaxiStatus;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.common.validation.ValidationException;

/**
 * Unit test for {@code Taxi}
 * 
 * @author Palraj
 */
public class TaxiTest {

	private final UUID taxiId = UUID.randomUUID();;

	private final String numberPlate = "Test Number Plate";

	private final Location location1 = new Location(1, 11.0, 77.0, "Bus terminal");

	private final Location location2 = new Location(2, 11.0, 77.0, "Railwau terminal");

	@Test
	public void testTaxiConstructorAndGetters() throws ValidationException {
		Taxi taxi = new Taxi(taxiId, numberPlate, TaxiStatus.AVAILABLE, location1);
		assertEquals(taxiId, taxi.getId());
		assertEquals(numberPlate, taxi.getNumberPlate());
		assertEquals(TaxiStatus.AVAILABLE, taxi.getCurrentStatus());
		assertEquals(location1, taxi.getCurrentLocation());
	}

	@Test
	public void testUpdateStatus_validTransition() throws ValidationException {
		Taxi taxi = new Taxi(taxiId, numberPlate, TaxiStatus.AVAILABLE, location1);
		taxi.updateStatus(TaxiStatus.BOOKED);
		assertEquals(TaxiStatus.BOOKED, taxi.getCurrentStatus());
	}

	@Test
	public void testTaxiCreationWithNulls() {
		UUID validId = UUID.randomUUID();
		String validPlate = "TN-07-XY-5678";
		assertThrows(NullPointerException.class, () -> new Taxi(null, validPlate, TaxiStatus.AVAILABLE, location1));
		assertThrows(NullPointerException.class, () -> new Taxi(validId, null, TaxiStatus.AVAILABLE, location1));
		assertThrows(NullPointerException.class, () -> new Taxi(validId, validPlate, null, location1));
		assertThrows(NullPointerException.class, () -> new Taxi(validId, validPlate, TaxiStatus.AVAILABLE, null));
	}

	@Test
	public void testUpdateStatus_invalidTransition() throws ValidationException {
		Taxi taxi = new Taxi(taxiId, numberPlate, TaxiStatus.RIDING, location1);
		ValidationException exception = assertThrows(ValidationException.class, () -> {
			taxi.updateStatus(TaxiStatus.BOOKED); // Not allowed
		});
		assertTrue(exception.getMessage().contains("not allowed"));
	}

	@Test
	public void testRespondToBooking_rejectsWhenNotAvailable() throws TaxiFleetException {
		Taxi taxi = new Taxi(taxiId, numberPlate, TaxiStatus.BOOKED, location1);
		BookingRequest request = BookingRequest.createRequest(UUID.randomUUID(), LocalDateTime.now(), location1,
				location2);
		BookingResponse response = taxi.respondToBooking(request);
		assertEquals(TaxiResponse.REJECTED, response.response());
		assertEquals(request.getUuid(), response.requestID());
		assertEquals(taxi.getId(), response.TaxiID());
	}

	@Test
	public void testRespondToBooking_rejectsEvenIfAvailable() throws TaxiFleetException {
		Taxi taxi = new Taxi(taxiId, numberPlate, TaxiStatus.AVAILABLE, location1);
		BookingRequest request = BookingRequest.createRequest(UUID.randomUUID(), LocalDateTime.now(), location1,
				location2);

		BookingResponse response = taxi.respondToBooking(request);
		assertEquals(TaxiResponse.REJECTED, response.response()); // currently hardcoded to reject
	}
}