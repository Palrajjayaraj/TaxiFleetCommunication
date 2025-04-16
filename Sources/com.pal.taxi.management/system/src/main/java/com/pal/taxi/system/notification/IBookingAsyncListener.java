package com.pal.taxi.system.notification;

import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;

/**
 * Listener , which will be notified related to booking requests.
 * 
 * @author Palraj
 */
public interface IBookingAsyncListener {

	/**
	 * notifies that the booking request is processed.
	 * 
	 * @param request The request which is processed, the state is already set, eg.
	 *                rejected or assigned taxi.
	 * @param booking the confirmed booking with the taxi information, if a taxi has
	 *                accepted it, if not accepted, then, {@code null} is returned
	 *                to indicate that the booking request is rejected.
	 */
	public void requestProcessed(BookingRequest request, Booking booking);

}
