package com.pal.taxi.persistence.internal.init;

import org.hibernate.Session;

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

}
