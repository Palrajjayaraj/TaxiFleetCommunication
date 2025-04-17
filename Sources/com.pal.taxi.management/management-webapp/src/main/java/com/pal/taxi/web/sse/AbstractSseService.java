package com.pal.taxi.web.sse;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.pal.taxi.common.booking.Booking;

public class AbstractSseService {

	public static record Subscription(UUID id, String type) {
	};

	public static final String BOOKING_CONFIRMATION = "booking-confirmation";

	/**
	 * use taxi ID as key, as taxi is not immutable and we should be able to find
	 * the taxi's with the given uuid.<br>
	 * For user, even though it is immutable, UUID is sufficient.
	 */
	protected final Map<Subscription, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEmitter subscribe(Subscription subscription) {
		SseEmitter emitter = new SseEmitter(0L); // No timeout
		emitters.put(subscription, emitter);
		emitter.onCompletion(() -> emitters.remove(subscription));
		emitter.onTimeout(() -> emitters.remove(subscription));
		emitter.onError(e -> emitters.remove(subscription));
		return emitter;
	}

	public void unsubscribe(Subscription subscription) {
		if (emitters.containsKey(subscription)) {
			emitters.remove(subscription);
		}
	}

	public void sendConfirmationBooking(UUID id, Booking booking) {
		Subscription subscription = new Subscription(id, BOOKING_CONFIRMATION);
		SseEmitter emitter = emitters.get(subscription);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().name(BOOKING_CONFIRMATION).data(booking));
			} catch (IOException e) {
				emitter.completeWithError(e);
				emitters.remove(subscription);
			}
		} else {
			// TODO this is a disastrous, what shall we do now that the taxi/user is
			// disconnected.
		}
	}

}
