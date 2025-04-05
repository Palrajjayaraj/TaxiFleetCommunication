package com.pal.taxi;

import java.util.UUID;

import com.pal.taxi.Taxi.TaxiResponse;

import lombok.NonNull;

public record BookingResponse(@NonNull UUID requestID, @NonNull UUID TaxiID, @NonNull TaxiResponse response) {
// this is used for inter process communication between Taxi and fleet management system, so, use minimal data
	// dont sent whole data, rather use only unique identifier.
}