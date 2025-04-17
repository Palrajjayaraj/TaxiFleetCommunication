package com.pal.taxi.system.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.filter.IFilter;
import com.pal.taxi.user.User;

public interface IPersistenceService {

	void createBookingRequest(BookingRequest request);

	void saveBookingRequest(BookingRequest request);

	Collection<BookingRequest> getBookingRequests(Collection<IFilter<BookingRequest>> filters);

	void saveBooking(Booking booking);

	Collection<Booking> getBookings(Collection<IFilter<Booking>> filters);

	void saveTaxi(Taxi taxi);

	void updateTaxiStatus(Taxi taxi);

	Collection<Taxi> getAllAvailableTaxis();

	Collection<Taxi> getAllTaxis();

	/**
	 * @return All the predefined locations from the DB
	 */
	Collection<Location> getLocations();

	/**
	 * @return All the users from the DB
	 */
	Collection<User> getUsers();

	/**
	 * 
	 * @param userId The user ID
	 * @return The user identified by the UserID
	 */
	Optional<User> getUser(UUID userId);

}
