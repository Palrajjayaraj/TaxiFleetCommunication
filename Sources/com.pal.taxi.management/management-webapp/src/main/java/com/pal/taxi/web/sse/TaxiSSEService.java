package com.pal.taxi.web.sse;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.pal.taxi.common.booking.BookingRequest;

@Service
public class TaxiSSEService extends AbstractSseService {

	public static final String BOOKING_REQUEST = "booking-request";

	/**
	 * send the request to the taxi identified by UUID, it is is still subsribed.
	 * 
	 * @param taxiId  the taxi to which the request to be sent
	 * @param request
	 */
	public void sendToTaxi(UUID taxiId, BookingRequest request) {
		Subscription subscription = new Subscription(taxiId, BOOKING_REQUEST);
		SseEmitter emitter = emitters.get(subscription);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().name(BOOKING_REQUEST).data(request, MediaType.APPLICATION_JSON));
			} catch (IOException e) {
				emitter.completeWithError(e);
				emitters.remove(subscription);
			}
		}
	}
}
