package com.pal.taxi.persistence.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.pal.taxi.common.booking.BookingRequest.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookingRequests")
public class BookingRequestEntity {

	public BookingRequestEntity() {
		// default constructor for Hibernate.
	}

	@Id
	private UUID uuid;

	@Column(nullable = false)
	private UUID userId;

	@Column(nullable = false)
	private LocalDateTime requestTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pickupLocationId", nullable = false)
	private LocationEntity pickupLocation;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dropoffLocationId", nullable = false)
	private LocationEntity dropoffLocation;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public LocalDateTime getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(LocalDateTime requestTime) {
		this.requestTime = requestTime;
	}

	public LocationEntity getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(LocationEntity pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public LocationEntity getDropoffLocation() {
		return dropoffLocation;
	}

	public void setDropoffLocation(LocationEntity dropoffLocation) {
		this.dropoffLocation = dropoffLocation;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
