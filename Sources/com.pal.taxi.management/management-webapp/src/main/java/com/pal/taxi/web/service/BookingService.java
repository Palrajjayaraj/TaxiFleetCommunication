package com.pal.taxi.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pal.taxi.common.booking.BookingRequest;

@Service
public class BookingService {

	@Autowired
	private TaxiFleetManagementService managementService;
	
	public void publishBookingRequest(BookingRequest request) {
		managementService.getManagement().publishBookingRequest(request);
	}

}
