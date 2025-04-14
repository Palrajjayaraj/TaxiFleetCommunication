package com.pal.taxi.persistence.internal.init;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;

import com.pal.taxi.persistence.entities.LocationEntity;

/**
 * initializes handful of predeinfed LocationEntitys into the DB.
 * 
 * @author Palraj
 */
public class LocationsInitializer implements IInitializer {

	private final List<LocationEntity> predefinedLocations = Arrays.asList(
			new LocationEntity(1, 11.0168, 76.9558, "Coimbatore City Center"),
			new LocationEntity(2, 11.0105, 76.9703, "Tidel Park Coimbatore"),
			new LocationEntity(3, 11.0202, 76.9609, "Brookefields Mall"),
			new LocationEntity(4, 11.0027, 76.9712, "RS Puram"),
			new LocationEntity(5, 11.0216, 76.9406, "Singanallur Lake"),
			new LocationEntity(6, 10.9918, 76.9551, "Peelamedu"),
			new LocationEntity(7, 11.0199, 76.9400, "Kovai Kondattam"),
			new LocationEntity(8, 10.9910, 76.9612, "PSG Tech"),
			new LocationEntity(9, 11.0300, 76.9568, "Coimbatore Airport"),
			new LocationEntity(10, 11.0119, 76.9574, "V.O.C. Park"),
			new LocationEntity(11, 11.0147, 76.9550, "Coimbatore Junction Railway Station"),
			new LocationEntity(12, 11.0162, 76.9380, "Kalingarayan Canal"),
			new LocationEntity(13, 11.0176, 76.9565, "Tidel Park Coimbatore"),
			new LocationEntity(14, 10.9931, 76.9615, "Isha Yoga Center"),
			new LocationEntity(15, 10.9915, 76.9319, "Alankar Theatre"),
			new LocationEntity(16, 11.0212, 76.9634, "Sai Baba Colony"),
			new LocationEntity(17, 11.0089, 76.9333, "Government Museum Coimbatore"),
			new LocationEntity(18, 11.0219, 76.9221, "Marudhamalai Temple"),
			new LocationEntity(19, 11.0153, 76.9600, "Cochin Garden"),
			new LocationEntity(20, 11.0205, 76.9374, "Azhiyur Village"),
			new LocationEntity(21, 11.0173, 76.9512, "Lawley Road"),
			new LocationEntity(22, 11.0156, 76.9355, "Saibaba Colony"),
			new LocationEntity(23, 11.0308, 76.9519, "Peelamedu Lake"),
			new LocationEntity(24, 11.0131, 76.9242, "Nehru Stadium"),
			new LocationEntity(25, 11.0223, 76.9378, "Gandhipuram Bus Stand"));

	@Override
	public void initialize(Session session) {
		for (LocationEntity entity : predefinedLocations) {
			session.persist(entity);
		}
	}

}
