package com.pal.taxi.persistence.internal.init;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;

import com.pal.taxi.Taxi.TaxiStatus;
import com.pal.taxi.persistence.entities.LocationEntity;
import com.pal.taxi.persistence.entities.TaxiEntity;

public class TaxiInitializer implements IInitializer {

	private static final Logger LOGGER = LogManager.getLogManager().getLogger(TaxiInitializer.class.getName());

	private static final int NO_OF_TAXIS = 20;

	@Override
	public void initialize(Session session) {
		Collection<LocationEntity> locations = getData(session, LocationEntity.class);
		initialize(session, new ArrayList<>(locations));
	}

	private void initialize(Session session, List<LocationEntity> locations) {
		for (int i = 0; i < NO_OF_TAXIS; i++) {
			String numberPlate = "TN01AA" + String.format("%04d", 1234 + i);
			LocationEntity location = locations.get(i % locations.size());
			TaxiEntity taxi = new TaxiEntity(UUID.randomUUID(), numberPlate, TaxiStatus.OFFLINE, location);
			session.persist(taxi);
		}
	}

}
