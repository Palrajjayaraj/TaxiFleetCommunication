package com.pal.taxi.persistence.mapper.internal;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.persistence.entities.BookingEntity;
import com.pal.taxi.persistence.entities.BookingRequestEntity;
import com.pal.taxi.persistence.entities.TaxiEntity;
import com.pal.taxi.persistence.internal.RepositoryRegistry;
import com.pal.taxi.system.persistence.PersistenceException;

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

	default BookingEntity toEntity(Booking booking) throws PersistenceException {
		BookingRequestEntity requestEntity = BookingRequestsMapper.INSTANCE.toEntity(booking.getRequest());
		Optional<TaxiEntity> taxi = RepositoryRegistry.getTaxiRepository()
				.findByNumberPlate(booking.getTaxi().getNumberPlate());
		assertTaxiFound(booking, taxi);
		return new BookingEntity(booking.getUuid(), requestEntity, taxi.get(), booking.getBookConfirmedTime(),
				booking.getStatus());
	}

	default void assertTaxiFound(Booking booking, Optional<TaxiEntity> taxi) throws PersistenceException {
		if (taxi.isEmpty()) {
			// not found in DB..
			throw new PersistenceException(String.format("There is no taxi with number %s present in DB.",
					booking.getTaxi().getNumberPlate()));
		}
	}

}