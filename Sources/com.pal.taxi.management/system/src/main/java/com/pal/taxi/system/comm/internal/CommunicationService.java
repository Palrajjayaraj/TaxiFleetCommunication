package com.pal.taxi.system.comm.internal;

import java.util.Collection;
import java.util.HashSet;

import com.pal.taxi.Taxi;
import com.pal.taxi.TaxiState;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.notification.IBookingListener;
import com.pal.taxi.system.notification.IBookingRequestNotifier;
import com.pal.taxi.user.User;

/**
 * The coomunication service for this system.<br>
 * System can receive or send data to specific sub modules.
 * 
 * @author Palraj
 */
public class CommunicationService implements IInternalBookingRequestNotifier {

	private final Collection<IBookingRequestNotifier> requestListeners = new HashSet<>();

	private final Collection<IBookingListener> bookingListeners = new HashSet<>();

	/**
	 * Communicates / Notifies the given list of taxis about a new booking request.
	 * 
	 * @param taxis          Collection of taxis to notify
	 * @param bookingRequest the booking request to notify about
	 */
	public void notifyTaxis(Collection<Taxi> taxis, BookingRequest bookingRequest) {
		requestListeners.forEach(listener -> listener.notifyTaxis(taxis, bookingRequest));
	}

	/**
	 * Communicates/ notifies that the booking is confirmed to the taxi as well as
	 * the user.
	 * 
	 * @param booking
	 * @param taxi
	 */
	public void notifyTaxi(Booking booking, Taxi taxi, User user) {
		bookingListeners.forEach(listener -> listener.accepted(booking.getRequest(), user, booking, taxi));
	}

	/**
	 * Gets the current state of a taxi
	 * 
	 * @param taxi the taxi to retrieve the status for.
	 * @return the current status of the taxi
	 */
	public TaxiState getTaxiState(Taxi taxi) {
		return null;
	}

	@Override
	public void addRequestNotifier(IBookingRequestNotifier notifier) {
		requestListeners.add(notifier);
	}

	public void addBookingListener(IBookingListener notifier) {
		bookingListeners.add(notifier);
	}

}