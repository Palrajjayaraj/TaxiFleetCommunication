package com.pal.taxi.web.internal.payload;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.validation.ValidationException;

@Mapper
public interface TaxiMapper{

	TaxiMapper INSTANCE = Mappers.getMapper(TaxiMapper.class);

	Taxi toTaxi(TaxiPayload entity)  throws ValidationException ;
}
