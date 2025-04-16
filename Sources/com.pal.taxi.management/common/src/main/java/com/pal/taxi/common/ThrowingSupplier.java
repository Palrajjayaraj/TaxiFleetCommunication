package com.pal.taxi.common;

/**
 * Similar to {@code Supplier}, but, throws exceptions particular to our system.
 * 
 * @param <T> The return type.
 */
public interface ThrowingSupplier<T> {

	T get() throws TaxiFleetException;

}
