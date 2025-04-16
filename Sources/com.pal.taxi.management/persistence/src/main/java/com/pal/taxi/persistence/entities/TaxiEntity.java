package com.pal.taxi.persistence.entities;

import java.util.UUID;

import com.pal.taxi.Taxi.TaxiStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "taxis")
public class TaxiEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "number_plate", nullable = false, unique = true)
	private String numberPlate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TaxiStatus currentStatus;

	@ManyToOne
	@JoinColumn(name = "currentLocation", nullable = false)
	private LocationEntity currentLocation;

	protected TaxiEntity() {
	}

	public TaxiEntity(UUID id, String numberPlate, TaxiStatus status, LocationEntity location) {
		setId(id);
		setNumberPlate(numberPlate);
		setCurrentStatus(status);
		setCurrentLocation(location);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}

	public LocationEntity getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(LocationEntity currentLocation) {
		this.currentLocation = currentLocation;
	}

	public TaxiStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(TaxiStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

}