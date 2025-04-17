package com.pal.taxi.system;

import java.util.Collection;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.TaxiFleetException;

public class TaxiManager implements IPersistenceServiceConsumer {

	/**
	 * @return provides all the predefined users.
	 */
	public Collection<Taxi> getAllTaxis() throws TaxiFleetException {
		return getPersistenceService().getAllTaxis();
	}

}
