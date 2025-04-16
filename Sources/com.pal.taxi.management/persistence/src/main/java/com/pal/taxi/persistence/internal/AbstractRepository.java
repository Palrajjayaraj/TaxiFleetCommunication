package com.pal.taxi.persistence.internal;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import jakarta.persistence.criteria.CriteriaQuery;

/**
 * Abstract implementation of a repository service, which gets everything and
 * finds a specific data with the given ID.
 * 
 * @param <T> The type of the data.
 */
public abstract class AbstractRepository<T> {

	/**
	 * @return The class of the entity. Must not be {@code null}
	 */
	protected abstract Class<T> getEntityClass();

	public List<T> getAll() {
		try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
			HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(getEntityClass());
			criteria.from(getEntityClass());
			return session.createQuery(criteria).getResultList();
		}
	}

	/**
	 * @param id The ID of the object
	 * @return finds the object by this ID and returns it.
	 */
	protected T findById(Object id) {
		try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
			return session.get(getEntityClass(), id);
		}
	}

}
