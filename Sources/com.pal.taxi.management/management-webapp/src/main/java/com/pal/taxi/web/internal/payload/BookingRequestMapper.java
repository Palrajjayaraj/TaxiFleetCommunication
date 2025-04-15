package com.pal.taxi.web.internal.payload;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;

public class BookingRequestMapper implements IPayloadToDomainMapper<BookingRequestPayload, BookingRequest> {

	@Override
	public BookingRequest map(BookingRequestPayload payload) throws TaxiFleetException {
		return BookingRequest.createRequest(payload.userId(), payload.requestTime(), payload.pickupLocation(),
				payload.dropoffLocation());
	}

}
