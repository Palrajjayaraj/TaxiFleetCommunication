package com.pal.taxi.web.service;

import org.springframework.stereotype.Service;

import com.pal.taxi.system.TaxiFleetManagement;

/**
 * service to have single instance of the {@code TaxiFleetManagement}
 * 
 * @author Palraj
 */
@Service
public class TaxiFleetManagementService {

	private final TaxiFleetManagement management = new TaxiFleetManagement();

	public TaxiFleetManagement getManagement() {
		return management;
	}

}
