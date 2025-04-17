package com.pal.taxi.web.service;

import org.springframework.stereotype.Service;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.system.TaxiManager;

/**
 * Taxi service to communicate with the taxi fleet system.
 */
@Service
public class TaxiService {

	public void updateState(Taxi taxi) throws ValidationException {
		new TaxiManager().updateState(taxi);
	}

}
