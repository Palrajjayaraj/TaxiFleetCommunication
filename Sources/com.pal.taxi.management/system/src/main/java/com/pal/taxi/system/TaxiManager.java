package com.pal.taxi.system;

import java.util.Collection;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.TaxiFleetException;

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
	 * @return provides currently available taxis.
	 */
	public Collection<Taxi> getAllAvailableTaxis() throws TaxiFleetException {
		return getPersistenceService().getAllAvailableTaxis();
	}

	/**
	 * @param taxi The taxi's state that need to be updated in the system.
	 * @throws TaxiFleetException
	 */
	public void updateState(Taxi taxi) throws TaxiFleetException {
		getPersistenceService().updateTaxiStatus(taxi);
	}

}
