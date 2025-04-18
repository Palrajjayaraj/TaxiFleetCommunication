package com.pal.taxi.common.lock;

import com.pal.taxi.common.TaxiFleetException;

public interface IRunnable<TH extends TaxiFleetException> {

	/**
	 * Runs this operation and throws specific exception.
	 */
	void run() throws TH;

}
