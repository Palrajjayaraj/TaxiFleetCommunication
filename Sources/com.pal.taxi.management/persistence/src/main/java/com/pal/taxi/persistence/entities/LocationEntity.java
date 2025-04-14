package com.pal.taxi.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The location entity to connect to read to and write from DB.
 */
@Entity
@Table(name = "locations")
public class LocationEntity {

	public LocationEntity() {
		// to avoid org.hibernate.InstantiationException: No default constructor for entity 'com.pal.taxi.persistence.entities.LocationEntity'
	}
	
	@Id
	private int id;

	private double latitude;
	
	private double longitude;
	
	private String readableName;

	public LocationEntity(int id, double latitude, double longitude, String readableName) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.readableName = readableName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getReadableName() {
		return readableName;
	}

	public void setReadableName(String readableName) {
		this.readableName = readableName;
	}
}
