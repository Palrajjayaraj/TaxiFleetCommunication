package com.pal.taxi.system;

import java.util.Collection;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;

/**
 * The manager which provides exclusive access to the bookings in the system.
 */
public class BookingManager implements IPersistenceServiceConsumer {

	/**
	 * @return provides all the bookings in the system.
	 */
	public Collection<Booking> getAllBookings() throws TaxiFleetException {
		return getPersistenceService().getBookings();
	}
	
	/**
	 * @return provides all the booking requests in the system.
	 */
	public Collection<BookingRequest> getAllBookingRequests() throws TaxiFleetException {
		return getPersistenceService().getAllBookingRequests();
	}

}
