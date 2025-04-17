package com.pal.taxi.system.filter;

import com.pal.taxi.Taxi;
import com.pal.taxi.Taxi.TaxiStatus;

public class AvailableTaxiFilter implements IFilter<Taxi> {

	@Override
	public boolean test(Taxi taxi) {
		return TaxiStatus.AVAILABLE.equals(taxi.getCurrentStatus());
	}

}
