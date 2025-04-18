package com.pal.taxi.persistence.internal.init;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import org.hibernate.Session;

import com.pal.taxi.common.booking.BookingRequest.Status;
import com.pal.taxi.persistence.entities.BookingRequestEntity;
import com.pal.taxi.persistence.entities.LocationEntity;
import com.pal.taxi.persistence.entities.UserEntity;

/**
 * initializes handful of predeinfed Booking request enity into the DB.
 * 
 * @author Palraj
 */
public class BookingRequestInitializer implements IInitializer {

	private final Random random = new Random();

	@Override
	public void initialize(Session session) {
		List<LocationEntity> locations = (List<LocationEntity>) getData(session, LocationEntity.class);
		List<UserEntity> users = (List<UserEntity>) getData(session, UserEntity.class);
		IntStream.rangeClosed(1, 7).forEach(dayOffset -> {
			int recordsToday = 5 + random.nextInt(6); // Random 5 to 10
			LocalDateTime baseTime = LocalDateTime.now().minusDays(7 - dayOffset);

			IntStream.range(0, recordsToday).forEach(i -> {
				BookingRequestEntity request = new BookingRequestEntity();
				request.setUuid(UUID.randomUUID());

				UserEntity user = users.get((i + dayOffset) % users.size());
				request.setUserId(user.getUuid());

				request.setRequestTime(baseTime.withHour(8 + random.nextInt(10)).withMinute(random.nextInt(60)));

				LocationEntity pickup = locations.get((i + dayOffset) % locations.size());
				LocationEntity dropoff = locations.get((i + dayOffset + 1) % locations.size());

				request.setPickupLocation(pickup);
				request.setDropoffLocation(dropoff);
				setStatus(request);
				session.persist(request);
			});
		});
	}

	private void setStatus(BookingRequestEntity request) {
		Status status = random.nextBoolean() ? Status.ASSIGNED_TAXI : Status.REJECTED;
		request.setStatus(status);
	}
}
