package com.pal.taxi.persistence.internal.init;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pal.taxi.persistence.entities.UserEntity;
import com.pal.taxi.persistence.internal.AppliationInitializationRepository;

public class DataInitializer {

	public void ensureDataInitialized(Session session) {
		// run this as a single operation
		AppliationInitializationRepository appInitRepo = new AppliationInitializationRepository(session);
		Transaction transaction = session.beginTransaction();
		if (!appInitRepo.isInitialized()) {
			insertInitialUsers(session);
			appInitRepo.setInitialized();
		}
		transaction.commit();
	}

	private void insertInitialUsers(Session session) {
		List<String> names = List.of("Alice Carter", "Benjamin Hayes", "Clara Sullivan", "David Morgan",
				"Emma Thompson", "Felix Grant", "Grace Bennett", "Henry Rhodes", "Isla Parker", "Jack Reynolds",
				"Katie Foster", "Liam Wallace", "Mia Douglas", "Noah Armstrong", "Olivia Chambers", "Patrick Monroe",
				"Quinn Stevens", "Ruby Dalton", "Samuel Everett", "Tessa Caldwell", "Uma Franklin", "Victor Nolan",
				"Willow Banks", "Xavier Clayton", "Zoe Barrett");

		for (String name : names) {
			UserEntity entity = new UserEntity();
			entity.setUuid(UUID.randomUUID());
			entity.setName(name);
			session.persist(entity);
		}
	}
}
