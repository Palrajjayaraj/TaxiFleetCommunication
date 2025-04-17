package com.pal.taxi.web.internal.payload;

import com.pal.taxi.Taxi.TaxiStatus;
import com.pal.taxi.common.Location;

/**
 * This is the taxi payload sent from front end to server.
 * 
 * @author Palraj
 */
public class TaxiPayload {

	private String id;

	private String numberPlate;

	private TaxiStatus currentStatus;

	private Location currentLocation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}

	public TaxiStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(TaxiStatus status) {
		this.currentStatus = status;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}
}
