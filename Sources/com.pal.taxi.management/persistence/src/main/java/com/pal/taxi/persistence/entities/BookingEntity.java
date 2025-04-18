package com.pal.taxi.persistence.entities;

import java.time.LocalDateTime;

import com.pal.taxi.common.booking.Booking.Status;

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
@Table(name = "booking")
public class BookingEntity {

	@Id
	private String uuid;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "booking_request_id", nullable = false)
	private BookingRequestEntity request;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "taxi_id", nullable = false)
	private TaxiEntity taxi;

	@Column(name = "book_confirmed_time", nullable = false)
	private LocalDateTime bookConfirmedTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private Status status;

	public BookingEntity() {
		// Required by Hibernate
	}

	public BookingEntity(String uuid, BookingRequestEntity request, TaxiEntity taxi, LocalDateTime bookConfirmedTime,
			Status status) {
		this.uuid = uuid;
		this.request = request;
		this.taxi = taxi;
		this.bookConfirmedTime = bookConfirmedTime;
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BookingRequestEntity getRequest() {
		return request;
	}

	public void setRequest(BookingRequestEntity request) {
		this.request = request;
	}

	public TaxiEntity getTaxi() {
		return taxi;
	}

	public void setTaxi(TaxiEntity taxi) {
		this.taxi = taxi;
	}

	public LocalDateTime getBookConfirmedTime() {
		return bookConfirmedTime;
	}

	public void setBookConfirmedTime(LocalDateTime bookConfirmedTime) {
		this.bookConfirmedTime = bookConfirmedTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
