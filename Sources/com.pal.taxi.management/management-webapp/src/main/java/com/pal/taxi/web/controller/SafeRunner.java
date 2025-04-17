package com.pal.taxi.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.ThrowingRunnable;
import com.pal.taxi.common.ThrowingSupplier;

/**
 * Safe runner, which catches any exception and returns with only the internal
 * server error message to the clients.
 * 
 * @author Palraj
 */
public class SafeRunner {

	public static <T> ResponseEntity<T> supply(ThrowingSupplier<T> supplier) {
		try {
			return ResponseEntity.ok(supplier.get());
		} catch (TaxiFleetException e) {
			// not required to set the stack trace to the front end.
			return ResponseEntity.internalServerError().build();
		}
	}

	public static ResponseEntity<HttpStatus> run(ThrowingRunnable runnable) {
		try {
			runnable.run();
			return ResponseEntity.ok().build();
		} catch (TaxiFleetException e) {
			// not required to set the stack trace to the front end.
			return ResponseEntity.internalServerError().build();
		}
	}

}
