package com.pal.taxi.web.service;

import org.springframework.stereotype.Service;

import com.pal.taxi.system.TaxiFleetManagement;

@Service
public class TaxiFleetManagementService {

	private final TaxiFleetManagement management = new TaxiFleetManagement();

	public TaxiFleetManagement getManagement() {
		return management;
	}

}
