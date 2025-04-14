package com.pal.taxi.persistence.internal;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import com.pal.taxi.persistence.entities.UserEntity;

import jakarta.persistence.criteria.CriteriaQuery;

public class UserRepository {

	public List<UserEntity> getAllUsers() {
		try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
			HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<UserEntity> criteria = builder.createQuery(UserEntity.class);
			criteria.from(UserEntity.class);
			return session.createQuery(criteria).getResultList();
		}
	}

	public UserEntity findById(UUID id) {
		try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
			return session.get(UserEntity.class, id);
		}
	}

	public static void main(String[] args) {
		UserRepository userRepository = new UserRepository();
		userRepository.getAllUsers();
	}
}
