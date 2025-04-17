package com.pal.taxi.persistence.mapper.internal;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.persistence.entities.BookingEntity;

/**
 * Mapper to convert between to Booking from DB Entity.
 */
@Mapper
public interface BookingMapper {
	BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

	default Booking toBooking(BookingEntity entity) throws TaxiFleetException {
		BookingRequest request = BookingRequestsMapper.INSTANCE.toRequest(entity.getRequest());
		Taxi taxi = TaxiMapper.INSTANCE.toTaxi(entity.getTaxi());
		return Booking.createBooking(request, taxi, entity.getBookConfirmedTime());
	}

}