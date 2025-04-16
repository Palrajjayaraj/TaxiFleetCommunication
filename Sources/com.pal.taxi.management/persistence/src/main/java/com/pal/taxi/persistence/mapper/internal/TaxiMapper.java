package com.pal.taxi.persistence.mapper.internal;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.persistence.entities.TaxiEntity;

/**
 * Mapper to convert between to Taxi
 */
@Mapper
public interface TaxiMapper {
	TaxiMapper INSTANCE = Mappers.getMapper(TaxiMapper.class);

	Taxi toTaxi(TaxiEntity entity) throws ValidationException;
}
