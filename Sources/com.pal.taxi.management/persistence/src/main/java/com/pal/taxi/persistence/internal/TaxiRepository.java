package com.pal.taxi.persistence.internal;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import com.pal.taxi.Taxi;
import com.pal.taxi.Taxi.TaxiStatus;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.persistence.entities.TaxiEntity;
import com.pal.taxi.persistence.mapper.internal.TaxiMapper;
import com.pal.taxi.system.persistence.PersistenceException;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class TaxiRepository extends AbstractRepository<TaxiEntity> {

	private TaxiRepository() {
		// single instance of repository.
	}

	private static final TaxiRepository SINGLE_INSTANCE = new TaxiRepository();

	public static TaxiRepository getInstance() {
		return SINGLE_INSTANCE;
	}

	@Override
	protected Class<TaxiEntity> getEntityClass() {
		return TaxiEntity.class;
	}

	/**
	 * @return all the taxis in the system.
	 */
	public Collection<Taxi> getAllTaxis() {
		return toTaxis(getAll());
	}

	private Collection<Taxi> toTaxis(Collection<TaxiEntity> taxiEntities) {
		return taxiEntities.stream().map(entity -> {
			try {
				return TaxiMapper.INSTANCE.toTaxi(entity);
			} catch (ValidationException ve) {
				// DB has the validated data and hence, this exception is not expected.
				LOGGER.error(ve.getMessage(), ve);
				// skip this taxi.
				return null;
			}
		}).filter(Taxi.class::isInstance).collect(Collectors.toSet());
	}

	/**
	 * @return all the available taxis in the system.
	 */
	public Collection<Taxi> getAvailableTaxis() {
		return lockRunner.runWithReadLock(() -> {
			try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
				HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<TaxiEntity> criteria = builder.createQuery(getEntityClass());
				Root<TaxiEntity> root = criteria.from(getEntityClass());
				criteria.select(root).where(builder.equal(root.get("currentStatus"), TaxiStatus.AVAILABLE));
				return toTaxis(session.createQuery(criteria).getResultList());
			}
		});
	}

	/**
	 * Updates the taxi status such as state and location to the DB.
	 * 
	 * @param taxi The taxi.
	 * @throws PersistenceException if anything goes wrong during persisting.
	 */
	public void updateTaxiStatus(Taxi taxi) throws PersistenceException {
		runWithWriteLock(() -> {
			TaxiEntity enity = TaxiMapper.INSTANCE.toEnity(taxi);
			try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
				Transaction tx = session.beginTransaction();
				session.merge(enity);
				tx.commit();
			}
		});
	}

	/**
	 * tries to find a taxi in the DB with the givne number plate.
	 * 
	 * @param numberPlate number plate.
	 * @return optional containing the taxi or empty, if not found.
	 */
	public Optional<TaxiEntity> findByNumberPlate(String numberPlate) {
		return lockRunner.runWithReadLock(() -> {
			try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
				CriteriaBuilder cb = session.getCriteriaBuilder();
				CriteriaQuery<TaxiEntity> cq = cb.createQuery(TaxiEntity.class);
				Root<TaxiEntity> root = cq.from(TaxiEntity.class);
				cq.select(root).where(cb.equal(root.get("numberPlate"), numberPlate));
				return session.createQuery(cq).uniqueResultOptional();
			}
		});
	}

}
