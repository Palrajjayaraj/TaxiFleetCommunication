package com.pal.taxi.system;

import java.util.Collection;

import com.pal.taxi.common.Location;
import com.pal.taxi.common.validation.ValidationException;

/**
 * The centralized manager, manages location in the whole system.
 */
public class LocationManager implements IPersistenceServiceConsumer {

	/**
	 * @return provides all the predefined locations.
	 */
	public Collection<Location> getLocations() throws ValidationException {
		return getPersistenceService().getLocations();
	}

}
