package com.pal.taxi.persistence.internal;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.common.lock.LockRunner;
import com.pal.taxi.persistence.entities.BookingRequestEntity;

/**
 * Responsible for managing the data to and fro the DB table for
 * BookingRequests.<br>
 * Uses batch mode to serialize data to the DB, as the requests can be incoming
 * mmore, in a second and hence save to DB using batch to improe performance.
 * 
 * @author Palraj
 */
public class BookingRequestRepository extends AbstractRepository<BookingRequestEntity> {

	private static final int BATCH_COUNT = 10;

	private final LinkedHashSet<BookingRequest> bookingRequests = new LinkedHashSet<>();

	private final ReadWriteLock bookingRequestLock = new ReentrantReadWriteLock(false);

	private final LockRunner lockRunner = new LockRunner(bookingRequestLock);

	/** single service to save or update to the DB. */
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	@Override
	protected Class<BookingRequestEntity> getEntityClass() {
		return BookingRequestEntity.class;
	}

	public void saveBookingRequest(BookingRequest request) {
		lockRunner.runSafelyWithWriteLock(() -> this.bookingRequests.add(request));
		saveDataInbatches();
	}

	private void saveDataInbatches() {
		executorService.execute(() -> {
			Integer dataSize = lockRunner.runWithReadLock(() -> bookingRequests.size());
			if (dataSize >= BATCH_COUNT) {
				Collection<BookingRequest> requests = new LinkedHashSet<>(BATCH_COUNT);
				lockRunner.runSafelyWithWriteLock(() -> {
					for (int index = 0; index < BATCH_COUNT; index++) {
						requests.add(bookingRequests.removeFirst());
					}
				});
				// now that we have taken the n number data from the list, we can execute
				// outside the scope of locks.
				try {
					serializeToDB(requests);
				} catch (TaxiFleetException e) {
					// roll back
					lockRunner.runSafelyWithWriteLock(() -> bookingRequests.addAll(requests));
				}
			}
		});

	}

	private void serializeToDB(Collection<BookingRequest> requests) throws TaxiFleetException {
		try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();
			try {
				for (BookingRequest request : requests) {
					session.persist(request);
				}
				session.flush();
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw new TaxiFleetException("Batch insert failed", e);
			}
		}
	}

}
