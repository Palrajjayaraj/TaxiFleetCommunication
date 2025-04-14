package com.pal.taxi.persistence.internal;

import com.pal.taxi.persistence.entities.UserEntity;

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
}
