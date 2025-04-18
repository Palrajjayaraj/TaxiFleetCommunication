package com.pal.taxi.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.web.internal.payload.TaxiResponsePayload;

@Service
public class BookingService {

	@Autowired
	private TaxiFleetManagementServicesFacade managementService;

	public void publishBookingRequest(BookingRequest request) throws TaxiFleetException {
		managementService.getManagement().publishBookingRequest(request);
	}

	public void processResponse(TaxiResponsePayload taxiResponse) {
		managementService.getManagement().processTaxiResponse(taxiResponse.getRequestID(), taxiResponse.getTaxiID(),
				taxiResponse.getResponse());
	}

}
