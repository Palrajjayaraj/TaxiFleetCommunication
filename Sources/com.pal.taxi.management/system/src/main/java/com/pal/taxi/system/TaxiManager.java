package com.pal.taxi.system;

import java.util.Collection;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.validation.ValidationException;

/**
 * The manager which manages the states of varius taxis.
 * 
 * @author Palraj
 */
public class TaxiManager implements IPersistenceServiceConsumer {

	/**
	 * @return provides all the predefined users.
	 */
	public Collection<Taxi> getAllTaxis() throws TaxiFleetException {
		return getPersistenceService().getAllTaxis();
	}

	/**
	 * @param taxi The taxi's state that need to be updated in the system.
	 * @throws ValidationException
	 */
	public void updateState(Taxi taxi) throws ValidationException {
		getPersistenceService().updateTaxiStatus(taxi);
	}

}
