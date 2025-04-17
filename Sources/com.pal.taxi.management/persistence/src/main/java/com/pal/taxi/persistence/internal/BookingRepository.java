package com.pal.taxi.persistence.internal;

import java.util.Collection;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.persistence.entities.BookingEntity;
import com.pal.taxi.persistence.mapper.internal.BookingMapper;

/**
 * The booking repository to fetch details from DB.
 * 
 * @author Palraj
 */
public class BookingRepository extends AbstractRepository<BookingEntity> {

	@Override
	protected Class<BookingEntity> getEntityClass() {
		return BookingEntity.class;
	}

	/**
	 * @return all the bookings from the repository.
	 */
	public Collection<Booking> getAllBookings() {
		return getAll().stream().map(entity -> {
			try {
				return BookingMapper.INSTANCE.toBooking(entity);
			} catch (TaxiFleetException tfe) {
				// already validated data only saved in DB.
				// TODO, what if someone changes in the DB.
			}
			return null;
		}).filter(Booking.class::isInstance).collect(Collectors.toSet());
	}

	/**
	 * Saves the booking to DB.
	 * 
	 * @param booking
	 */
	public void saveBooking(Booking booking) {
		try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();
			session.persist(booking);
			session.flush();
			session.clear();
			tx.commit();
		}
	}

}
