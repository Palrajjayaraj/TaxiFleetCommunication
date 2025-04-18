package com.pal.taxi.persistence.internal;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.persistence.IPersistenceService;
import com.pal.taxi.system.persistence.PersistenceException;
import com.pal.taxi.user.User;

/**
 * This is a service provided thru java service loader mechanishm and hence,
 * this is initialized only once.
 */
public class PersistenceServiceImpl implements IPersistenceService {

	/**
	 * Initialises the service and adds default values to the DB, if they are not
	 * available.
	 */
	public PersistenceServiceImpl() {
		SessionFactoryProvider.getInstance();
	}

	@Override
	public void createBookingRequest(BookingRequest request) throws PersistenceException {
		bookingRequestRepo.createBookingRequest(request);
	}

	@Override
	public void saveBookingRequest(BookingRequest request) throws PersistenceException {
		bookingRequestRepo.saveBookingRequest(request);
	}

	@Override
	public Collection<BookingRequest> getAllBookingRequests() {
		return bookingRequestRepo.getAllRequests();
	}

	@Override
	public void saveBooking(Booking booking) throws TaxiFleetException {
		bookingRepository.saveBooking(booking);
	}

	@Override
	public Collection<Booking> getBookings() {
		return bookingRepository.getAllBookings();
	}

	@Override
	public void saveTaxi(Taxi taxi) {

	}

	@Override
	public void updateTaxiStatus(Taxi taxi)  throws PersistenceException{
		taxiRepository.updateTaxiStatus(taxi);
	}

	@Override
	public Collection<Taxi> getAllAvailableTaxis() {
		return taxiRepository.getAvailableTaxis();
	}

	@Override
	public Collection<Location> getLocations() {
		return locationRepository.getAllLocations();
	}

	private final LocationRepository locationRepository = RepositoryRegistry.getLocationRepository();

	private final UserRepository userRepository = RepositoryRegistry.getUserRepository();

	private final BookingRequestRepository bookingRequestRepo = RepositoryRegistry.getBookingRequestRepository();

	private final TaxiRepository taxiRepository = RepositoryRegistry.getTaxiRepository();

	private final BookingRepository bookingRepository = RepositoryRegistry.getBookingRepository();

	@Override
	public Collection<User> getUsers() {
		return userRepository.getAllUsers();
	}

	@Override
	public Collection<Taxi> getAllTaxis() {
		return taxiRepository.getAllTaxis();
	}

	@Override
	public Optional<User> getUser(UUID userId) {
		return userRepository.getUser(userId);
	}

}
