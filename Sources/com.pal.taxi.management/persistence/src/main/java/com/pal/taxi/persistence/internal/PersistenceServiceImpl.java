package com.pal.taxi.persistence.internal;

import java.util.Collection;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.filter.IFilter;
import com.pal.taxi.system.persistence.IPersistenceService;
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
		HibernateUtil.getInstance();
	}

	@Override
	public void saveBookingRequest(BookingRequest request) {
		bookingRequestRepo.saveBookingRequest(request);
	}

	@Override
	public Collection<BookingRequest> getBookingRequests(Collection<IFilter<BookingRequest>> filters) {
		return null;
	}

	@Override
	public void saveBooking(Booking booking) {

	}

	@Override
	public Collection<Booking> getBookings(Collection<IFilter<Booking>> filters) {
		return null;
	}

	@Override
	public void saveTaxi(Taxi taxi) {

	}

	@Override
	public void updateTaxiStatus(Taxi taxi) {

	}

	@Override
	public Collection<Taxi> getAllAvailableTaxis() {
		return taxiRepository.getAvailableTaxis();
	}

	@Override
	public Collection<Location> getLocations() {
		return locationRepository.getAllLocations();
	}

	private final LocationRepository locationRepository = new LocationRepository();

	private final UserRepository userRepository = new UserRepository();

	private final BookingRequestRepository bookingRequestRepo = new BookingRequestRepository();

	private final TaxiRepository taxiRepository = new TaxiRepository();

	@Override
	public Collection<User> getUsers() {
		return userRepository.getAllUsers();
	}

}
