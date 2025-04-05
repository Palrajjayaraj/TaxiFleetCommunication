package com.pal.taxi.common.lock;

import com.pal.taxi.common.TaxiFleetException;

public interface IRunnable {

	/**
	 * Runs this operation.
	 */
	void run() throws TaxiFleetException;

}
