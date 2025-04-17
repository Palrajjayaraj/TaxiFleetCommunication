package com.pal.taxi.web.sse;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.pal.taxi.common.booking.Booking;

public class AbstractSseService {

	private static final String BOOKING_CONFIRMATION = "booking-confirm";

	/**
	 * use taxi ID as key, as taxi is not immutable and we should be able to find
	 * the taxi's with the given uuid.<br>
	 * For user, even though it is immutable, UUID is sufficient.
	 */
	protected final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEmitter subscribe(UUID taxiId) {
		SseEmitter emitter = new SseEmitter(0L); // No timeout
		emitters.put(taxiId, emitter);
		emitter.onCompletion(() -> emitters.remove(taxiId));
		emitter.onTimeout(() -> emitters.remove(taxiId));
		emitter.onError(e -> emitters.remove(taxiId));
		return emitter;
	}

	public void unsubscribe(UUID id) {
		if (emitters.containsKey(id)) {
			emitters.remove(id);
		}
	}

	public void sendConfirmationBooking(UUID id, Booking booking) {
		SseEmitter emitter = emitters.get(id);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().name(BOOKING_CONFIRMATION).data(booking));
			} catch (IOException e) {
				emitter.completeWithError(e);
				emitters.remove(id);
			}
		} else {
			// TODO this is a disastrous, what shall we do now that the taxi/user is
			// disconnected.
		}
	}

}
