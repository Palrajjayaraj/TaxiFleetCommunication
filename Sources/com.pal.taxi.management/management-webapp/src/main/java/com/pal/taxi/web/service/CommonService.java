package com.pal.taxi.web.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.pal.taxi.common.Location;
import com.pal.taxi.system.comm.CommunicationService;
import com.pal.taxi.user.User;

/**
 * common serivce to fetch pre defined details such as location, user details,
 * taxi details, system admin, etc.,<br>
 * delegates to the comm layer to fetch such details.
 */
@Service
public class CommonService {

	public Collection<Location> getAllLocations() {
		return new CommunicationService().getLocations();
	}

	public Collection<User> getAllUsers() {
		return new CommunicationService().getUsers();
	}

}
