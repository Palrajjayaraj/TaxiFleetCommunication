package com.pal.taxi.web.internal.payload;

import java.util.UUID;

import com.pal.taxi.Taxi.TaxiResponse;

/**
 * payload sent by the client.
 */
public class TaxiResponsePayload {

	private UUID taxiID;

	private UUID requestID;

	private TaxiResponse response;

	public UUID getTaxiID() {
		return taxiID;
	}

	public void setTaxiID(UUID taxiID) {
		this.taxiID = taxiID;
	}

	public UUID getRequestID() {
		return requestID;
	}

	public void setRequestID(UUID requestID) {
		this.requestID = requestID;
	}

	public TaxiResponse getResponse() {
		return response;
	}

	public void setResponse(TaxiResponse response) {
		this.response = response;
	}

}
