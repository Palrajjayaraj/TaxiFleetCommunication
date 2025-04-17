package com.pal.taxi.web.sse;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.pal.taxi.web.sse.AbstractSseService.Subscription;

@RestController
@RequestMapping("/notifications/taxi")
public class TaxiSSEController {

	private final TaxiSSEService sseService;

	public TaxiSSEController(TaxiSSEService sseService) {
		this.sseService = sseService;
	}

	@GetMapping(value = "/bookingsRequest/subscribe/{taxiId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribeForRequest(@PathVariable UUID taxiId) {
		return sseService.subscribe(new Subscription(taxiId, TaxiSSEService.BOOKING_REQUEST));
	}

	@GetMapping(value = "/bookings/subscribe/{taxiId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribeForBooking(@PathVariable UUID taxiId) {
		return sseService.subscribe(new Subscription(taxiId, AbstractSseService.BOOKING_CONFIRMATION));
	}
}
