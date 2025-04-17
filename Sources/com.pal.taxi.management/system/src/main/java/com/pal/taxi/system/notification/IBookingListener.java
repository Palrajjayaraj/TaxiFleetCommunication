package com.pal.taxi.system.notification;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.user.User;

/**
 * Listener , which will be notified when a booking is confirmed
 * 
 * @author Palraj
 */
public interface IBookingListener {

	/**
	 * notifies that the booking request is processed and converted to booking,
	 * successfully
	 * 
	 * @param request The request which is processed, the state is already set, eg.
	 *                assigned taxi.
	 * @param booking the confirmed booking with the taxi information, if a taxi has
	 *                accepted it,
	 */
	public void accepted(BookingRequest request, User user, Booking booking, Taxi taxi);

	/**
	 * Notifies that the booking request is processed and the system is unable to
	 * find any taxis for it.
	 * 
	 * @param request The request
	 * @param user    The origin user.
	 */
	public void noTaxisFound(BookingRequest request, User user);

}
