package com.pal.taxi.persistence.internal;

import org.hibernate.Session;
import org.hibernate.Transaction;

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

}
