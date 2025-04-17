package com.pal.taxi.persistence.mapper.internal;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.persistence.entities.BookingRequestEntity;

/**
 * Mapper to convert between to BookingRequest from DB Entity.
 */
@Mapper
public interface BookingRequestsMapper {
	BookingRequestsMapper INSTANCE = Mappers.getMapper(BookingRequestsMapper.class);

	default BookingRequest toRequest(BookingRequestEntity entity) throws TaxiFleetException {
		Location pickUp = LocationMapper.INSTANCE.toLocation(entity.getPickupLocation());
		Location dropOff = LocationMapper.INSTANCE.toLocation(entity.getDropoffLocation());
		return BookingRequest.createRequest(entity.getUuid(), entity.getUserId(), entity.getRequestTime(), pickUp,
				dropOff, entity.getStatus());
	}
	
	BookingRequestEntity toEntity(BookingRequest request) ;
}