package com.pal.taxi.web.sse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaxiSSEService {

	private final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEmitter subscribe(UUID taxiId) {
		SseEmitter emitter = new SseEmitter(0L); // No timeout
		emitters.put(taxiId, emitter);
		emitter.onCompletion(() -> emitters.remove(taxiId));
		emitter.onTimeout(() -> emitters.remove(taxiId));
		emitter.onError(e -> emitters.remove(taxiId));
		return emitter;
	}

	public void sendToTaxi(UUID taxiId, Object data) {
		SseEmitter emitter = emitters.get(taxiId);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().name("booking-request").data(data));
			} catch (IOException e) {
				emitter.completeWithError(e);
				emitters.remove(taxiId);
			}
		}
	}
}
