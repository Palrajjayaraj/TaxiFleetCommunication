package com.pal.taxi.web.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.validation.ValidationException;
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

	public Collection<Location> getAllLocations() throws ValidationException {
		return new LocationManager().getLocations();
	}

	public Collection<User> getAllUsers() throws TaxiFleetException {
		return new UserManager().getUsers();
	}
	
	public Collection<Taxi> getAllTaxis() throws TaxiFleetException {
		return new TaxiManager().getAllTaxis();
	}

}
