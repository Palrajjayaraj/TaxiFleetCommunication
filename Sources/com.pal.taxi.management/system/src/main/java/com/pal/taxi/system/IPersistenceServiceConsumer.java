package com.pal.taxi.system;

import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.system.persistence.IPersistenceService;
import com.pal.taxi.system.persistence.PersistenceServiceProvider;

/**
 * defines that the given entity is a consumer of persistence service.
 * 
 * @author Palraj
 */
public interface IPersistenceServiceConsumer {

	/**
	 * @return The persistence service
	 * @throws ValidationException in case no implementatoin of the persistence
	 *                             service is present.<br>
	 *                             This is a huge blocker.
	 */
	public default IPersistenceService getPersistenceService() throws ValidationException {
		return new PersistenceServiceProvider().get();
	}

}
