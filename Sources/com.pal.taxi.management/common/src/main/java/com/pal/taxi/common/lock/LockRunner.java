package com.pal.taxi.common.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.Function;
import java.util.function.Supplier;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.ThrowingRunnable;

import lombok.NonNull;

/**
 * Runner which defines specific method to run specific piece of code, with read
 * or write lock.
 */
public class LockRunner {

	private final ReadWriteLock lock;

	public LockRunner(@NonNull ReadWriteLock lock) {
		this.lock = lock;
	}

	/** Runs the given code after acquiring the read lock. */
	public <T> T runWithReadLock(@NonNull Supplier<T> code) {
		try {
			this.lock.readLock().lock();
			return code.get();
		} finally {
			this.lock.readLock().unlock();
		}
	}

	/** Runs the given code after acquiring the write lock. */
	public void runWithWriteLock(@NonNull ThrowingRunnable code) throws TaxiFleetException {
		try {
			this.lock.writeLock().lock();
			code.run();
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	/**
	 * Runs the given code after acquiring the write lock, provides functionality to
	 * convert to a speific exeption.
	 */
	public <TH extends TaxiFleetException> void runWithWriteLock(@NonNull IRunnable<TH> code,
			Function<TaxiFleetException, TH> toSpecificException) throws TH {
		try {
			this.lock.writeLock().lock();
			code.run();
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	/**
	 * Runs the given code after acquiring the write lock and it expects no
	 * exception to be thrown.
	 */
	public void runSafelyWithWriteLock(@NonNull Runnable code) {
		try {
			this.lock.writeLock().lock();
			code.run();
		} finally {
			this.lock.writeLock().unlock();
		}
	}

}
