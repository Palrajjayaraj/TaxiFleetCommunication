package com.pal.taxi.system;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.system.comm.CommunicationService;
import com.pal.taxi.system.filter.AvailableTaxiFilter;
import com.pal.taxi.system.persistence.IPersistenceService;

/**
 * Responsible for managing the booking requests and assigning the taxis.
 * 
 * @author Palraj
 */
public class TaxiFleetManagement implements IPersistenceServiceConsumer {

	private Optional<IPersistenceService> persistenceService = Optional.empty();

	/**
	 * call this during the application loading to ensure that the communication
	 * service is initialized andrelated modules such as persistence layer is also
	 * initialized
	 */
	public void initialize() throws ValidationException {
		persistenceService = Optional.of(getPersistenceService());
	}

	private final CommunicationService commService = new CommunicationService();

	/**
	 * Publishes a new booking request to the system. usually, this is sent from the
	 * user to the system.
	 * 
	 * @param request the booking request to be published
	 */
	public void publishBookingRequest(BookingRequest request) {
		// 1. save the request to the DB
		// 2. collect various available taxis
		// 3. send notification to the available taxi.
		persistenceService.ifPresent(service -> {
			service.saveBookingRequest(request);
			Collection<Taxi> availableTaxis = service.getAllAvailableTaxis();
			commService.notifyTaxis(availableTaxis, request);
		});
	}

}
