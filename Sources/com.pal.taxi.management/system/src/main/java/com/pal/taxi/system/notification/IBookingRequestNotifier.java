package com.pal.taxi.system.notification;

import java.util.Collection;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.booking.BookingRequest;

public interface IBookingRequestNotifier {
	/**
	 * Communicates / Notifies the given list of taxis about a new booking request.
	 * 
	 * @param taxis          Collection of taxis to notify
	 * @param bookingRequest the booking request to notify about
	 */
	public void notifyTaxis(Collection<Taxi> taxis, BookingRequest bookingRequest);
}
