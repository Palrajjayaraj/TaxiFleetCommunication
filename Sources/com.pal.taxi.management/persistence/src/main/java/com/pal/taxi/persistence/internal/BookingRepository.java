package com.pal.taxi.persistence.internal;

import java.util.Collection;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.persistence.entities.BookingEntity;
import com.pal.taxi.persistence.mapper.internal.BookingMapper;
import com.pal.taxi.system.persistence.PersistenceException;

/**
 * The booking repository to fetch details from DB.
 * 
 * @author Palraj
 */
public class BookingRepository extends AbstractRepository<BookingEntity> {

	private BookingRepository() {
		// single instance of repository.
	}

	private static final BookingRepository SINGLE_INSTANCE = new BookingRepository();

	public static BookingRepository getInstance() {
		return SINGLE_INSTANCE;
	}

	@Override
	protected Class<BookingEntity> getEntityClass() {
		return BookingEntity.class;
	}

	/**
	 * @return all the bookings from the repository.
	 */
	public Collection<Booking> getAllBookings() {
		try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
			return getAll(session).stream().map(entity -> {
				try {
					return BookingMapper.INSTANCE.toBooking(entity);
				} catch (TaxiFleetException tfe) {
					// already validated data only saved in DB.
					// TODO, what if someone changes in the DB.
					LOGGER.error("Failed to fetch bookings", tfe);
				}
				return null;
			}).filter(Booking.class::isInstance).collect(Collectors.toSet());
		}
	}

	/**
	 * Saves the booking to DB.
	 * 
	 * @param booking
	 * @throws PersistenceException
	 */
	public void saveBooking(Booking booking) throws PersistenceException {
		try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();
			BookingEntity entity = BookingMapper.INSTANCE.toEntity(booking);
			session.persist(entity);
			session.flush();
			session.clear();
			tx.commit();
		}
	}

}
