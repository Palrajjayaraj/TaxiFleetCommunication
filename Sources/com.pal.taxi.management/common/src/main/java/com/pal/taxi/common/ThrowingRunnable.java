package com.pal.taxi.common;

/**
 * Similar to {@code Runnable}, but, throws exceptions particular to our system.
 * 
 */
public interface ThrowingRunnable {
	void run() throws TaxiFleetException;
}