package com.pal.taxi.persistence.internal;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pal.taxi.common.lock.IRunnable;
import com.pal.taxi.common.lock.LockRunner;
import com.pal.taxi.system.persistence.PersistenceException;

import jakarta.persistence.criteria.CriteriaQuery;

/**
 * Abstract implementation of a repository service, which gets everything and
 * finds a specific data with the given ID.
 * 
 * @param <T> The type of the data.
 */
public abstract class AbstractRepository<T> {

	protected final ReadWriteLock repoLock = new ReentrantReadWriteLock(true);

	protected final LockRunner lockRunner = new LockRunner(repoLock);

	/** logger for logging in the repositories. */
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepository.class);

	/**
	 * @return The class of the entity. Must not be {@code null}
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * uses existing session for fetching the data from DB.
	 */
	public List<T> getAll(Session session) {
		return lockRunner.runWithReadLock(() -> {
			HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(getEntityClass());
			criteria.from(getEntityClass());
			return session.createQuery(criteria).getResultList();
		});
	}

	public List<T> getAll() {
		return lockRunner.runWithReadLock(() -> {
			try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
				return getAll(session);
			}
		});
	}

	/**
	 * @param id The ID of the object
	 * @return finds the object by this ID and returns it.
	 */
	public T findById(Object id) {
		return lockRunner.runWithReadLock(() -> {
			try (Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
				return session.get(getEntityClass(), id);
			}
		});
	}

	protected void runWithWriteLock(IRunnable<PersistenceException> runnable) throws PersistenceException {
		lockRunner.runWithWriteLock(runnable, PersistenceException::new);
	}

}
