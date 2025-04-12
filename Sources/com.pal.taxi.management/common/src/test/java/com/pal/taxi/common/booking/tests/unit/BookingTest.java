package com.pal.taxi.common.booking.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Test;
import org.mockito.Mockito;

import com.pal.taxi.common.ITaxiInfo;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;

/**
 * Junit for {@code Booking}
 */
public class BookingTest {

	private final Location pickup = new Location(1, 12.9716, 77.5946, "Prozone Mall");

	private final Location dropoff = new Location(2, 12.9352, 77.6146, "Bus Terminal");

	private final UUID userID = UUID.randomUUID();

	@Test
	public void testCreateBookingSuccess() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		LocalDateTime confirmTime = LocalDateTime.now();

		Booking booking = Booking.createBooking(request, taxi, confirmTime);

		assertNotNull(booking.getUuid());
		assertEquals(request, booking.getRequest());
		assertEquals(taxi, booking.getTaxi());
		assertEquals(confirmTime, booking.getBookConfirmedTime());
		assertEquals(Booking.Status.CONFIRMED, booking.getStatus());
		assertNull(booking.getRide());
	}

	@Test
	public void testStartRideSuccess() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		LocalDateTime confirmTime = LocalDateTime.now();
		Booking booking = Booking.createBooking(request, taxi, confirmTime);

		booking.startRide();

		assertEquals(Booking.Status.IN_PROGRESS, booking.getStatus());
		assertNotNull(booking.getRide());
		assertNull(booking.getRide().getEndTime());
	}

	@Test
	public void testCompleteRideSuccess() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		LocalDateTime confirmTime = LocalDateTime.now();
		Booking booking = Booking.createBooking(request, taxi, confirmTime);

		booking.startRide();
		booking.completeRide();

		assertEquals(Booking.Status.COMPLETED, booking.getStatus());
		assertNotNull(booking.getRide());
		assertNotNull(booking.getRide().getEndTime());
	}

	@Test
	public void testCancelRideSuccess() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		LocalDateTime confirmTime = LocalDateTime.now();
		Booking booking = Booking.createBooking(request, taxi, confirmTime);

		booking.cancelRide();

		assertEquals(Booking.Status.CANCELLED, booking.getStatus());
		assertNull(booking.getRide());
	}

	/* Negatives */

	@Test(expected = NullPointerException.class)
	public void testCreateBookingWithNullRequest() throws TaxiFleetException {
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		LocalDateTime confirmTime = LocalDateTime.now();
		Booking.createBooking(null, taxi, confirmTime);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateBookingWithNullTaxi() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		LocalDateTime confirmTime = LocalDateTime.now();
		Booking.createBooking(request, null, confirmTime);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateBookingWithNullConfirmationTime() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		Booking.createBooking(request, taxi, null);
	}

	@Test
	public void testStartRideWhenAlreadyInProgress() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		LocalDateTime confirmTime = LocalDateTime.now();
		Booking booking = Booking.createBooking(request, taxi, confirmTime);

		booking.startRide();

		assertThrows("The booking is already in-progress.", TaxiFleetException.class, () -> booking.startRide());
	}

	@Test
	public void testUpdateStatusToNullFails() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		LocalDateTime confirmTime = LocalDateTime.now();
		Booking booking = Booking.createBooking(request, taxi, confirmTime);

		assertThrows("Status cannot be set to null. provide valid status", TaxiFleetException.class,
				() -> booking.updateStatus(null));
	}

	@Test
	public void testUpdateStatusAfterCompletedFails() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		LocalDateTime confirmTime = LocalDateTime.now();
		Booking booking = Booking.createBooking(request, taxi, confirmTime);

		booking.startRide();
		booking.completeRide();

		assertThrows("The booking is already closed and cannot set any more status.", TaxiFleetException.class,
				() -> booking.updateStatus(Booking.Status.CANCELLED));
	}

	@Test
	public void testCancelAfterCompletionFails() throws TaxiFleetException {
		BookingRequest request = BookingRequest.createRequest(userID, LocalDateTime.now(), pickup, dropoff);
		ITaxiInfo taxi = Mockito.mock(ITaxiInfo.class);
		LocalDateTime confirmTime = LocalDateTime.now();
		Booking booking = Booking.createBooking(request, taxi, confirmTime);

		booking.startRide();
		booking.completeRide();

		assertThrows("The booking is already closed and cannot set any more status.", TaxiFleetException.class,
				() -> booking.cancelRide());
	}

}