package com.pal.taxi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.web.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	// Endpoint to create a booking
	@PostMapping("/request")
	public String requestBooking(@RequestBody BookingRequest bookingRequest) {
//		UUID bookingId = bookingService.processBookingRequest(bookingRequest);
		return "Booking Request submitted successfully. Booking ID: " ;
	}
}
