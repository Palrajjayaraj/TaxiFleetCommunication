package com.pal.taxi.web.sse;

import org.springframework.stereotype.Component;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.notification.IBookingRequestNotifier;

import java.util.Collection;

/**
 * The notifier, which send the requests to the given available taxis.
 * 
 * @author Palraj
 */
@Component
public class SseBookingRequestNotifier implements IBookingRequestNotifier {

	private final TaxiSSEService sseService;

	public SseBookingRequestNotifier(TaxiSSEService sseService) {
		this.sseService = sseService;
	}

	@Override
	public void notifyTaxis(Collection<Taxi> taxis, BookingRequest bookingRequest) {
		for (Taxi taxi : taxis) {
			sseService.sendToTaxi(taxi.getId(), bookingRequest);
		}
	}

}
