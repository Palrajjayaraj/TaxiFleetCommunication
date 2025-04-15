package com.pal.taxi.persistence.mapper.internal;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.pal.taxi.persistence.entities.UserEntity;
import com.pal.taxi.user.User;

/**
 * Mapper to convert between to User from DB UserEntity.
 */
@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User toUser(UserEntity entity);
}
