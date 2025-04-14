package com.pal.taxi.web.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.pal.taxi.common.Location;
import com.pal.taxi.system.comm.CommunicationService;

/**
 * location serivce to fetch location, delegates to the comm layer to fetch
 * location.
 */
@Service
public class LocationService {

	public Collection<Location> getAllLocations() {
		return new CommunicationService().getLocations();
	}

}
