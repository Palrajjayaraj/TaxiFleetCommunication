package com.pal.taxi.system.filter;

import java.time.LocalDateTime;

import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.validation.ValidationException;

import lombok.NonNull;

/**
 * filter to filter the booking was confirmed between given start and end time.
 * 
 * @author Palraj
 */
public class BookingDateTimeRangeFilter extends DateTimeRangeFilter<Booking> {

	public BookingDateTimeRangeFilter(@NonNull LocalDateTime startDateTime, @NonNull LocalDateTime endDateTime)
			throws ValidationException {
		super(startDateTime, endDateTime);
	}

	@Override
	protected LocalDateTime getDateTime(Booking item) {
		return item.getBookConfirmedTime();
	}

}
