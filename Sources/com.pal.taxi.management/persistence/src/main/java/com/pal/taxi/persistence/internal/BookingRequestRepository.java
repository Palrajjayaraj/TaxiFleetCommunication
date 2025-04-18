package com.pal.taxi.persistence.internal;

import java.util.Collection;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.persistence.entities.BookingRequestEntity;
import com.pal.taxi.persistence.mapper.internal.BookingRequestsMapper;

/**
 * Responsible for managing the data to and fro the DB table for
 * BookingRequests.<br>
 * 
 * @author Palraj
 */
public class BookingRequestRepository extends AbstractRepository<BookingRequestEntity> {

	private BookingRequestRepository() {
		// single instance of repository.
	}

	private static final BookingRequestRepository SINGLE_INSTANCE = new BookingRequestRepository();

	public static BookingRequestRepository getInstance() {
		return SINGLE_INSTANCE;
	}

	@Override
	protected Class<BookingRequestEntity> getEntityClass() {
		return BookingRequestEntity.class;
	}

	public void createBookingRequest(BookingRequest request) {
		try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();
			BookingRequestEntity entity = BookingRequestsMapper.INSTANCE.toEntity(request);
			session.persist(entity);
			session.flush();
			session.clear();
			tx.commit();
		}
	}

	public void saveBookingRequest(BookingRequest request) {
		try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();
			BookingRequestEntity entity = BookingRequestsMapper.INSTANCE.toEntity(request);
			session.merge(entity);
			session.flush();
			session.clear();
			tx.commit();
		}
	}

	/**
	 * @return all the booking requests from the repository.
	 */
	public Collection<BookingRequest> getAllRequests() {
		try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
			return getAll(session).stream().map(entity -> {
				try {
					return BookingRequestsMapper.INSTANCE.toRequest(entity);
				} catch (TaxiFleetException tfe) {
					// already validated data only saved in DB.
					// TODO, what if someone changes in the DB.
					LOGGER.error("Failed to fetch bookings", tfe);
				}
				return null;
			}).filter(BookingRequest.class::isInstance).collect(Collectors.toSet());
		}
	}

}
