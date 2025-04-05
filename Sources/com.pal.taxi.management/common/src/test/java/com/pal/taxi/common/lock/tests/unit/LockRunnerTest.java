package com.pal.taxi.common.lock.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.lock.LockRunner;

/** Junit for {@link LockRunner} */
public class LockRunnerTest {

	private Lock mockReadLock;

	private Lock mockWriteLock;

	private LockRunner lockRunner;

	@Before
	public void setUp() {
		ReadWriteLock mockReadWriteLock = Mockito.mock(ReadWriteLock.class);
		mockReadLock = Mockito.mock(Lock.class);
		mockWriteLock = Mockito.mock(Lock.class);
		when(mockReadWriteLock.readLock()).thenReturn(mockReadLock);
		when(mockReadWriteLock.writeLock()).thenReturn(mockWriteLock);
		lockRunner = new LockRunner(mockReadWriteLock);
	}

	@Test
	public void testRunWithReadLockReturnsValue() {
		String result = lockRunner.runWithReadLock(() -> "Hello Taxi");
		assertEquals("Hello Taxi", result);
		verifyLockCalls(1, 0);
	}

	private void verifyLockCalls(int readCount, int writeCount) {
		verify(mockReadLock, times(readCount)).lock();
		verify(mockReadLock, times(readCount)).unlock();
		verify(mockWriteLock, times(writeCount)).lock();
		verify(mockWriteLock, times(writeCount)).unlock();
	}

	@Test
	public void testRunWithWriteLockExecutesCode() throws TaxiFleetException {
		final boolean[] executed = { false };
		lockRunner.runWithWriteLock(() -> executed[0] = true);
		assertTrue("Write lock code should execute", executed[0]);
		verifyLockCalls(0, 1);
	}

	@Test
	public void testReadLockIsReleasedAfterException() throws TaxiFleetException {
		try {
			lockRunner.runWithReadLock(() -> {
				throw new RuntimeException("exception during execution");
			});
			fail("Expected TaxiFleetException to be thrown");
		} catch (RuntimeException re) {
			// expected
			assertEquals("exception during execution", re.getMessage());
		}
		verifyLockCalls(1, 0);
	}

	@Test
	public void testWriteLockIsReleasedAfterException() throws TaxiFleetException {
		try {
			lockRunner.runWithWriteLock(() -> {
				throw new TaxiFleetException("execution during execution");
			});
			fail("Expected TaxiFleetException to be thrown");
		} catch (TaxiFleetException tfe) {
			// expected
		}
		verifyLockCalls(0, 1);
	}
}
