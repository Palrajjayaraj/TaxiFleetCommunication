package com.pal.taxi.common;

/**
 * Base checked exception for the whole application.
 * 
 * @author Palraj
 */
public class TaxiFleetException extends Exception {

	private static final long serialVersionUID = 4827212099621411369L;

	/**
	 * Constructs a new TaxiFleetException with the specified detail message.
	 *
	 * @param message The detail message for the exception.
	 */
	public TaxiFleetException(String message) {
		super(message);
	}

	/**
	 * Constructs a new TaxiException with the specified detail message and cause.
	 *
	 * @param message The detail message for the exception.
	 * @param cause   The cause of the exception.
	 */
	public TaxiFleetException(String message, Throwable cause) {
		super(message, cause);
	}
}
