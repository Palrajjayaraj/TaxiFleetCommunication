package com.pal.taxi.system;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.pal.taxi.Taxi;
import com.pal.taxi.Taxi.TaxiResponse;
import com.pal.taxi.common.ITaxiInfo;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.common.booking.BookingRequest.Status;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.system.booking.internal.BookingManagerConfig;
import com.pal.taxi.system.booking.internal.BookingRequestsManager;
import com.pal.taxi.system.comm.internal.CommunicationService;
import com.pal.taxi.system.notification.IBookingListener;
import com.pal.taxi.system.notification.IBookingRequestNotifier;
import com.pal.taxi.system.persistence.IPersistenceService;
import com.pal.taxi.system.persistence.PersistenceException;
import com.pal.taxi.user.User;

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

	private final BookingRequestsManager requestsManager = new BookingRequestsManager(this, new TaxiManager(),
			BookingManagerConfig.defaults());

	/**
	 * Publishes a new booking request to the system. usually, this is sent from the
	 * user to the system.
	 * 
	 * @param request the booking request to be published
	 * @throws PersistenceException from persistence layer.
	 */
	public void publishBookingRequest(BookingRequest request) throws PersistenceException {
		if (persistenceService.isPresent()) {
			persistenceService.get().createBookingRequest(request);
		}
		requestsManager.submitBookingRequest(request);
	}

	/**
	 * Notifies the taxis about the booking request.
	 * 
	 * @param taxis   The taxis to be notified
	 * @param request The booking request.
	 */
	public void notifyTaxis(Collection<Taxi> taxis, BookingRequest request) {
		this.commService.notifyTaxis(taxis, request);
	}

	/**
	 * notifies the system that there are no taxis currently available to process
	 * this request.
	 * 
	 * @param request the request to be processed.
	 */
	public void noTaxiFound(BookingRequest request) {

	}

	/**
	 * System identified that the taxi is available and the taxi also confirmed that
	 * the booking shall be taken, confirm this booking.
	 * 
	 * @param request the booking request.
	 * @param taxi    The taxi to be confirmed.
	 * @throws TaxiFleetException if anything goes wrong during the creation of
	 *                            booking.
	 */
	public void bookTaxi(BookingRequest request, Taxi taxi) throws TaxiFleetException {
		TaxiFleetException[] exception = new TaxiFleetException[] { null };
		this.persistenceService.ifPresent(persistence -> {
			Optional<User> user = persistence.getUser(request.getUserId());
			if (user.isPresent()) {
				try {
					Booking booking = Booking.createBooking(request, ITaxiInfo.class.cast(taxi), LocalDateTime.now());
					request.updateStatus(Status.ASSIGNED_TAXI);
					this.commService.notifyTaxi(booking, taxi, user.get());
					persistence.saveBookingRequest(request);
					persistence.saveBooking(booking);
				} catch (TaxiFleetException e) {
					exception[0] = e;
				}
			} else {
				exception[0] = new TaxiFleetException(
						"Unable to find user for UUID , from which the request have been raised.: "
								+ request.getUserId());
			}
		});
		if (null != exception[0]) {
			throw exception[0];
		}
	}

	public void processTaxiResponse(UUID requestID, UUID taxiID, TaxiResponse response) {
		requestsManager.receiveTaxiResponse(requestID, taxiID, response);
	}

	public void addBookingRequestNotifier(IBookingRequestNotifier notifier) {
		if (null != notifier) {
			this.commService.addRequestNotifier(notifier);
		}
	}

	public void addBookingListener(IBookingListener notifier) {
		if (null != notifier) {
			this.commService.addBookingListener(notifier);
		}
	}

}
