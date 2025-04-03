package com.pal.taxi.common.validation;

import com.pal.taxi.common.TaxiFleetException;

/**
 * The validation exception indicates that a critical validation has failed and
 * the application should handle it.
 */
public class ValidationException extends TaxiFleetException {

	public ValidationException(String message, Throwable exception) {
		super(message, exception);
	}

	private static final long serialVersionUID = 8201167829523607623L;

}
