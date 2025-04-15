package com.pal.taxi.web.internal.payload;

import com.pal.taxi.common.TaxiFleetException;

/**
 * A mapper interface that can convert various payload to domain objects
 * 
 * @author Palraj
 */
public interface IPayloadToDomainMapper<Payload, Domain> {

	Domain map(Payload payload) throws TaxiFleetException;

}
