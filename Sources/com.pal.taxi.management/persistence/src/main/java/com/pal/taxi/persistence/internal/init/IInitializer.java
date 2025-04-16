package com.pal.taxi.persistence.internal.init;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import jakarta.persistence.criteria.CriteriaQuery;

/**
 * the interface which defines that a table data can be initializer.
 * 
 * @author Palraj
 */
public interface IInitializer {

	/**
	 * @param session The session, which can be used to initialize specific data.
	 */
	public void initialize(Session session);

	/**
	 * Searches the given session for the given entity.
	 */
	public default <T> Collection<T> getData(Session session, Class<T> entityClass) {
		HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(entityClass);
		criteria.from(entityClass);
		return session.createQuery(criteria).getResultList();
	}

}
