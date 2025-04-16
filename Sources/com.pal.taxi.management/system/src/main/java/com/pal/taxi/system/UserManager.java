package com.pal.taxi.system;

import java.util.Collection;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.user.User;

/**
 * The centralized manager, manages users in the whole system.
 */
public class UserManager implements IPersistenceServiceConsumer {

	/**
	 * @return provides all the predefined users.
	 */
	public Collection<User> getUsers() throws TaxiFleetException {
		return getPersistenceService().getUsers();
	}

}
