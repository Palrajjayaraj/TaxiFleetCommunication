package com.pal.taxi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.web.internal.payload.BookingRequestMapper;
import com.pal.taxi.web.internal.payload.BookingRequestPayload;
import com.pal.taxi.web.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping("/request")
	public String requestBooking(@RequestBody BookingRequestPayload bookingRequest) throws TaxiFleetException {
		BookingRequest req = new BookingRequestMapper().map(bookingRequest);
		
		return "Booking Request submitted successfully. Booking ID: " ;
	}
}
