package com.pal.taxi.web.internal.payload;

import java.time.LocalDateTime;
import java.util.UUID;

import com.pal.taxi.common.Location;

/**
 * This is the initial booking request payload sent from the front end to the
 * back end.
 * 
 * @author Palraj
 */
public record BookingRequestPayload(UUID userId, LocalDateTime requestTime, Location pickupLocation,
		Location dropoffLocation) {
}
