package com.pal.taxi.system.persistence;

import java.util.Optional;
import java.util.ServiceLoader;

import com.pal.taxi.common.validation.ValidationException;

/**
 * The provider which provides {@link IPersistenceService}
 * 
 * @author Palraj
 */
public class PersistenceServiceProvider {

	private static Optional<IPersistenceService> service;

	static {
		loadService();
	}

	private static void loadService() {
		ServiceLoader<IPersistenceService> loader = ServiceLoader.load(IPersistenceService.class);
		service = loader.findFirst();
	}

	private void assertServiceLoaded() throws ValidationException {
		service.orElseThrow(() -> new ValidationException("No IPersistenceService implementation found!"));
	}

	/**
	 * @return The
	 */
	public IPersistenceService get() throws ValidationException {
		assertServiceLoaded();
		return service.get();
	}
}
