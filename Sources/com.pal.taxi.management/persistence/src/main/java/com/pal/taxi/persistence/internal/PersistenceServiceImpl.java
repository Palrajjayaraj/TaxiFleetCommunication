package com.pal.taxi.persistence.internal;

import java.util.Collection;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.filter.IFilter;
import com.pal.taxi.system.persistence.IPersistenceService;
import com.pal.taxi.user.User;

public class PersistenceServiceImpl implements IPersistenceService {

	@Override
	public void saveBookingRequest(BookingRequest request) {

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
	public Collection<Taxi> getTaxis(Collection<IFilter<Taxi>> filters) {
		return null;
	}

	@Override
	public Collection<Location> getLocations() {
		return locationRepository.getAllLocations();
	}

	private final LocationRepository locationRepository = new LocationRepository();

	private final UserRepository userRepository = new UserRepository();
	
	@Override
	public Collection<User> getUsers() {
		return userRepository.getAllUsers();
	}

}
