package com.pal.taxi.persistence.mapper.internal;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.pal.taxi.common.Location;
import com.pal.taxi.persistence.entities.LocationEntity;

/**
 * Mapper to convert between to Location
 */
@Mapper
public interface LocationMapper {
	LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

	Location toLocation(LocationEntity entity);
}
