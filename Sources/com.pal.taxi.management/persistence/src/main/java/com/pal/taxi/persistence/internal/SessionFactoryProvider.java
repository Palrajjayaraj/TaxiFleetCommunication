package com.pal.taxi.persistence.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.pal.taxi.persistence.entities.AppliationInitializationEntity;
import com.pal.taxi.persistence.entities.BookingEntity;
import com.pal.taxi.persistence.entities.BookingRequestEntity;
import com.pal.taxi.persistence.entities.LocationEntity;
import com.pal.taxi.persistence.entities.TaxiEntity;
import com.pal.taxi.persistence.entities.UserEntity;
import com.pal.taxi.persistence.internal.init.DataInitializer;

/**
 * provides session factory
 * 
 * @author Palraj
 */
public class SessionFactoryProvider {

	private static final class INSTANCE_HOLDER {

		private static final SessionFactoryProvider INSTANCE = new SessionFactoryProvider();

	}

	public static SessionFactoryProvider getInstance() {
		return INSTANCE_HOLDER.INSTANCE;
	}

	private SessionFactoryProvider() {
		// singleton pattern.
		try (Session session = sessionFactory.openSession()) {
			dataInitializer.ensureDataInitialized(session);
		}
	}

	private final SessionFactory sessionFactory = buildSessionFactory();

	private final DataInitializer dataInitializer = new DataInitializer();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration config = new Configuration();
			Properties settings = new Properties();
			// if we dont go a step back and go again into persistence, when it is loaded
			// from web-app
			// it is trying to search for data in the web-app resources folder.
			try (InputStream inputStream = SessionFactoryProvider.class.getClassLoader()
					.getResourceAsStream("hibernate.properties")) {
				if (inputStream == null) {
					throw new FileNotFoundException("hibernate.properties not found in classpath");
				}
				settings.load(inputStream);
			}
			config.setProperties(settings);
			config.addAnnotatedClass(UserEntity.class);
			config.addAnnotatedClass(AppliationInitializationEntity.class);
			config.addAnnotatedClass(LocationEntity.class);
			config.addAnnotatedClass(BookingRequestEntity.class);
			config.addAnnotatedClass(TaxiEntity.class);
			config.addAnnotatedClass(BookingEntity.class);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties())
					.build();
			return config.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError("SessionFactory creation failed: " + ex);
		}
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
}
