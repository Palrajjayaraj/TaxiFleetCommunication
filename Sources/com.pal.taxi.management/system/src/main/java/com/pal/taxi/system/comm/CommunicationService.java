package com.pal.taxi.system.comm;

import java.util.Collection;
import java.util.List;

import com.pal.taxi.Taxi;
import com.pal.taxi.TaxiState;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.system.persistence.IPersistenceService;
import com.pal.taxi.system.persistence.PersistenceServiceProvider;
import com.pal.taxi.user.User;

/**
 * The coomunication service for this system.<br>
 * System can receive or send data to specific sub modules.
 * 
 * @author Palraj
 */
public class CommunicationService {

	/**
	 * call this during the application loading to ensure that the communication
	 * service is initialized andrelated modulkes such as persistence layer is also
	 * initialized
	 */
	public void initialize() {

	}

	/**
	 * Publishes a new booking request to the system. usually, this is sent from the
	 * user to the system.
	 * 
	 * @param request the booking request to be published
	 */
	public void publishBookingRequest(BookingRequest request) {

	}

	/**
	 * Communicates / Notifies the given list of taxis about a new booking request.
	 * 
	 * @param taxis          list of taxis to notify
	 * @param bookingRequest the booking request to notify about
	 */
	public void notifyTaxis(List<Taxi> taxis, BookingRequest bookingRequest) {

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

	private IPersistenceService persistenceService;

	public CommunicationService() {
		try {
			persistenceService = new PersistenceServiceProvider().get();
		} catch (ValidationException e) {
			persistenceService = null;
		}
	}

	/**
	 * @return provides all the predefined locations.
	 */
	public Collection<Location> getLocations() {
		return persistenceService.getLocations();
	}
	
	/**
	 * @return provides all the predefined users.
	 */
	public Collection<User> getUsers() {
		return persistenceService.getUsers();
	}
	
}