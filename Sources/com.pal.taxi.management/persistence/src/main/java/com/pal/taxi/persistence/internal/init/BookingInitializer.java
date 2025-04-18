package com.pal.taxi.persistence.internal.init;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import org.hibernate.Session;

import com.pal.taxi.common.booking.BookingRequest.Status;
import com.pal.taxi.persistence.entities.BookingEntity;
import com.pal.taxi.persistence.entities.BookingRequestEntity;
import com.pal.taxi.persistence.entities.TaxiEntity;

/**
 * initializes handful of predefined Booking entities into the DB.
 * 
 * @author Palraj
 */
public class BookingInitializer implements IInitializer {

	private final Random random = new Random();

	@Override
	public void initialize(Session session) {
		List<BookingRequestEntity> confirmedRequests = ((List<BookingRequestEntity>) getData(session,
				BookingRequestEntity.class)).stream().filter(r -> r.getStatus() == Status.ASSIGNED_TAXI).toList();
		List<TaxiEntity> taxis = (List<TaxiEntity>) getData(session, TaxiEntity.class);

		IntStream.range(0, confirmedRequests.size()).forEach(i -> {
			BookingRequestEntity request = confirmedRequests.get(i);
			TaxiEntity taxi = taxis.get(i % taxis.size());

			BookingEntity booking = new BookingEntity(UUID.randomUUID().toString(), request, taxi,
					request.getRequestTime().plusMinutes(10 + random.nextInt(20)),
					com.pal.taxi.common.booking.Booking.Status.CONFIRMED);
			session.persist(booking);
		});
	}
}
