package com.pal.taxi.web.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.common.booking.BookingRequest.Status;
import com.pal.taxi.web.internal.payload.BookingRequestsConversion;
import com.pal.taxi.web.internal.payload.BookingTrend;

/**
 * Service responsible for various reports data.
 */
@Service
public class ReportsService {

	private final CommonService commonService;

	public ReportsService(CommonService commonService) {
		this.commonService = commonService;
	}

	public Collection<BookingTrend> getBookingTrends() throws TaxiFleetException {
		Collection<Booking> allBookings = commonService.getAllBookings();
		Collection<BookingTrend> trends = new TreeSet<>(Comparator.nullsLast(Comparator.comparing(BookingTrend::date)));
		Map<LocalDate, Integer> dateToCountMap = new HashMap<>();
		for (Booking booking : allBookings) {
			LocalDate localDate = booking.getBookConfirmedTime().toLocalDate();
			dateToCountMap.computeIfAbsent(localDate, key -> 0);
			dateToCountMap.computeIfPresent(localDate, (t, u) -> u + 1);
		}
		for (Map.Entry<LocalDate, Integer> entry : dateToCountMap.entrySet()) {
			trends.add(new BookingTrend(entry.getKey(), entry.getValue()));
		}
		return trends;
	}

	private static final class RequestToBookingCount {
		private int requestCount = 0;

		private int bookingCount = 0;

	}

	public Collection<BookingRequestsConversion> getBookingRequestsTrends() throws TaxiFleetException {
		Collection<BookingRequest> requests = commonService.getAllBookingRequests();
		Map<LocalDate, RequestToBookingCount> dateToCountMap = new HashMap<>();
		Collection<BookingRequestsConversion> trends = new TreeSet<>(
				Comparator.nullsLast(Comparator.comparing(BookingRequestsConversion::date)));
		for (BookingRequest req : requests) {
			LocalDate date = req.getRequestTime().toLocalDate();
			dateToCountMap.computeIfAbsent(date, key -> new RequestToBookingCount());
			dateToCountMap.get(date).requestCount = dateToCountMap.get(date).requestCount + 1;
			if (Status.ASSIGNED_TAXI.equals(req.getStatus())) {
				dateToCountMap.get(date).bookingCount = dateToCountMap.get(date).bookingCount + 1;
			}
		}
		for (Map.Entry<LocalDate, RequestToBookingCount> entry : dateToCountMap.entrySet()) {
			trends.add(new BookingRequestsConversion(entry.getKey(), entry.getValue().requestCount,
					entry.getValue().bookingCount));
		}
		return trends;
	}

}
