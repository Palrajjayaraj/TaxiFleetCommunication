package com.pal.taxi.web.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.system.BookingManager;
import com.pal.taxi.system.LocationManager;
import com.pal.taxi.system.TaxiManager;
import com.pal.taxi.system.UserManager;
import com.pal.taxi.user.User;

/**
 * common serivce to fetch pre defined details such as location, user details,
 * taxi details, system admin, etc.,<br>
 * delegates to the comm layer to fetch such details.
 */
@Service
public class CommonService {

	private final LocationManager locationManager = new LocationManager();

	private final UserManager userManager = new UserManager();

	private final TaxiManager taxiManager = new TaxiManager();

	private final BookingManager bookingManager = new BookingManager();

	public Collection<Location> getAllLocations() throws ValidationException {
		return locationManager.getLocations();
	}

	public Collection<User> getAllUsers() throws TaxiFleetException {
		return userManager.getUsers();
	}

	public Collection<Taxi> getAllTaxis() throws TaxiFleetException {
		return taxiManager.getAllTaxis();
	}

	public Collection<Booking> getAllBookings() throws TaxiFleetException {
		return bookingManager.getAllBookings();
	}

	public Collection<BookingRequest> getAllBookingRequests() throws TaxiFleetException {
		return bookingManager.getAllBookingRequests();
	}

}
