package com.pal.taxi.web.sse;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/taxis")
public class TaxiSseController {

    private final TaxiSSEService sseService;

    public TaxiSseController(TaxiSSEService sseService) {
        this.sseService = sseService;
    }

    @GetMapping(value = "/subscribe/{taxiId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable UUID taxiId) {
        return sseService.subscribe(taxiId);
    }
}
