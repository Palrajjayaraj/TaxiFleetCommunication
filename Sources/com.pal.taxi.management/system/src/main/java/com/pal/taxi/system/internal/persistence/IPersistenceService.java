package com.pal.taxi.system.internal.persistence;

import com.pal.taxi.Taxi;
import java.util.Collection;

import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;

public interface IPersistenceService {

	void saveBookingRequest(BookingRequest request);

	Collection<BookingRequest> getBookingRequests(Collection<IFilter<BookingRequest>> filters);

	void saveBooking(Booking booking);

	Collection<Booking> getBookings(Collection<IFilter<Booking>> filters);

	void saveTaxi(Taxi taxi);

	void updateTaxiStatus(Taxi taxi);

	Collection<Taxi> getTaxis(Collection<IFilter<Taxi>> filters);
}
