package com.pal.taxi.persistence.internal;

import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import com.pal.taxi.persistence.entities.AppliationInitializationEntity;

import jakarta.persistence.criteria.CriteriaQuery;

public class AppliationInitializationRepository {

	private final Session session;

	public AppliationInitializationRepository(Session session) {
		this.session = session;
	}

	public boolean isInitialized() {
		HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AppliationInitializationEntity> criteria = builder
				.createQuery(AppliationInitializationEntity.class);
		criteria.from(AppliationInitializationEntity.class);
		return !session.createQuery(criteria).getResultList().isEmpty();
	}

	public void setInitialized() {
		session.persist(new AppliationInitializationEntity(Boolean.TRUE.toString()));
	}
}
