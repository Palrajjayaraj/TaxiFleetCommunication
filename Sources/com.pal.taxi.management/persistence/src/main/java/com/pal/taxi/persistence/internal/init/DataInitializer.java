package com.pal.taxi.persistence.internal.init;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pal.taxi.persistence.internal.AppliationInitializationRepository;

public class DataInitializer {

	private final List<IInitializer> initializers = List.of(new UserDetailsInitializer(), new LocationsInitializer());

	public void ensureDataInitialized(Session session) {
		// run this as a single operation
		AppliationInitializationRepository appInitRepo = new AppliationInitializationRepository(session);
		Transaction transaction = session.beginTransaction();
		if (!appInitRepo.isInitialized()) {
			for (IInitializer initializer : initializers) {
				initializer.initialize(session);
			}
			appInitRepo.setInitialized();
		}
		transaction.commit();
	}

}
