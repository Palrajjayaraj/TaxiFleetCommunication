package com.pal.taxi.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pal.taxi.system.TaxiFleetManagement;
import com.pal.taxi.web.service.TaxiFleetManagementServicesFacade;

/**
 * The starting point of the web app.
 * 
 * @author Palraj
 */
@SpringBootApplication
public class TaxiWebApplication implements CommandLineRunner {

	private final TaxiFleetManagementServicesFacade managementService;

	public TaxiWebApplication(TaxiFleetManagementServicesFacade managementService) {
		this.managementService = managementService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TaxiWebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// This would connect to DB and load initial values.
		managementService.getManagement().initialize();
	}

}
