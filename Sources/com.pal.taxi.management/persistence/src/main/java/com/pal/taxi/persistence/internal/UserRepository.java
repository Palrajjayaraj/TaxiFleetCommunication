package com.pal.taxi.persistence.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.pal.taxi.common.Location;
import com.pal.taxi.persistence.entities.UserEntity;
import com.pal.taxi.persistence.mapper.internal.UserMapper;
import com.pal.taxi.user.User;

/**
 * The user repository.
 * 
 * @author Palraj
 */
public class UserRepository extends AbstractRepository<UserEntity> {

	@Override
	protected Class<UserEntity> getEntityClass() {
		return UserEntity.class;
	}

	/**
	 * as of now, the users wont change and hence, there is no need to go again and
	 * again to fetch the data from DB server.
	 */
	private final Collection<User> users = new TreeSet<>(getAllUsersFromRepo());

	private Collection<User> getAllUsersFromRepo() {
		return getAll().stream().map(UserMapper.INSTANCE::toUser).collect(Collectors.toSet());
	}

	/**
	 * @return All the predefined users stored in the DB.
	 */
	public Collection<User> getAllUsers() {
		return Collections.unmodifiableCollection(users);
	}
}
