package com.pal.taxi.web.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.web.internal.payload.BookingRequestsConversion;
import com.pal.taxi.web.internal.payload.BookingTrend;
import com.pal.taxi.web.service.ReportsService;

@RestController
@RequestMapping("/report")
public class ReportController {

	private final ReportsService reportsService;

	public ReportController(ReportsService reportsService) {
		this.reportsService = reportsService;
	}

	@GetMapping("bookings/trend")
	public Collection<BookingTrend> getBookingTrends() throws TaxiFleetException {
		return this.reportsService.getBookingTrends();
	}

	@GetMapping("bookingRequests/trend")
	public Collection<BookingRequestsConversion> getBookingRequestsTrends() throws TaxiFleetException {
		return this.reportsService.getBookingRequestsTrends();
	}

}