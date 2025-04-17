package com.pal.taxi.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.web.internal.payload.BookingRequestMapper;
import com.pal.taxi.web.internal.payload.BookingRequestPayload;
import com.pal.taxi.web.internal.payload.TaxiResponsePayload;
import com.pal.taxi.web.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping("/request")
	public ResponseEntity<BookingRequest> requestBooking(@RequestBody BookingRequestPayload bookingRequest)
			throws TaxiFleetException {
		BookingRequest req = new BookingRequestMapper().map(bookingRequest);
		bookingService.publishBookingRequest(req);
		return ResponseEntity.ok(req);
	}

	@PostMapping("/taxi/response")
	public void response(@RequestBody TaxiResponsePayload taxiResponse) {
		bookingService.processResponse(taxiResponse);
	}
}
