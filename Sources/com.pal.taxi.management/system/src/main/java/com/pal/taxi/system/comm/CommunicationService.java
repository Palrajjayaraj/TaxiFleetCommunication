package com.pal.taxi.system.comm;

import java.util.Collection;

import com.pal.taxi.Taxi;
import com.pal.taxi.TaxiState;
import com.pal.taxi.common.booking.BookingRequest;

/**
 * The coomunication service for this system.<br>
 * System can receive or send data to specific sub modules.
 * 
 * @author Palraj
 */
public class CommunicationService {

	/**
	 * Communicates / Notifies the given list of taxis about a new booking request.
	 * 
	 * @param taxis          Collection of taxis to notify
	 * @param bookingRequest the booking request to notify about
	 */
	public void notifyTaxis(Collection<Taxi> taxis, BookingRequest bookingRequest) {

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

}