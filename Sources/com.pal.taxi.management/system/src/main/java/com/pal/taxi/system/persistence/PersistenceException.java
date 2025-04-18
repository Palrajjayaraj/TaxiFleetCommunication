package com.pal.taxi.system.persistence;

import com.pal.taxi.common.TaxiFleetException;

/**
 * Represents the exception in the persistenec layer.
 * 
 * @author Palraj
 */
public class PersistenceException extends TaxiFleetException {

	public PersistenceException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	/**
	 * Constructs a new PersistenceException with the specified detail message.
	 *
	 * @param message The detail message for the exception.
	 */
	public PersistenceException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -2344062532058184309L;

}
