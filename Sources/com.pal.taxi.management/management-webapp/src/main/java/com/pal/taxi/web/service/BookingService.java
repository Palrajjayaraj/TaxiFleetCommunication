package com.pal.taxi.web.service;

import org.springframework.stereotype.Service;

import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.comm.CommunicationService;

@Service
public class BookingService {

	public void publishBookingRequest(BookingRequest request) {
		new CommunicationService().publishBookingRequest(request);
	}

}
