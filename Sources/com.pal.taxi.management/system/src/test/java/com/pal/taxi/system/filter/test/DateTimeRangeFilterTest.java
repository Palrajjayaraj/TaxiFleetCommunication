package com.pal.taxi.system.filter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.system.filter.DateTimeRangeFilter;

/**
 * Unit test class for {@code DateTimeRangeFilter}
 * 
 * @author Palraj
 */
public class DateTimeRangeFilterTest {

	private static class TestItem {
		private final LocalDateTime timestamp;

		private TestItem(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}
	}

	private static class TestBookingDateTimeRangeFilter extends DateTimeRangeFilter<TestItem> {
		public TestBookingDateTimeRangeFilter(LocalDateTime start, LocalDateTime end) throws ValidationException {
			super(start, end);
		}

		@Override
		protected LocalDateTime getDateTime(TestItem item) {
			return item.getTimestamp();
		}
	}

	@Test
	public void dateTimeIsWithinRange() throws ValidationException {
		LocalDateTime start = LocalDateTime.of(2025, 4, 10, 0, 0);
		LocalDateTime end = LocalDateTime.of(2025, 4, 12, 23, 59);
		TestItem item = new TestItem(LocalDateTime.of(2025, 4, 11, 10, 0));
		DateTimeRangeFilter<TestItem> filter = new TestBookingDateTimeRangeFilter(start, end);
		assertTrue(filter.test(item));
	}

	@Test
	public void dateIsBeforeRange() throws ValidationException {
		LocalDateTime start = LocalDateTime.of(2025, 4, 10, 0, 0);
		LocalDateTime end = LocalDateTime.of(2025, 4, 12, 23, 59);
		TestItem item = new TestItem(LocalDateTime.of(2025, 4, 9, 23, 59));
		DateTimeRangeFilter<TestItem> filter = new TestBookingDateTimeRangeFilter(start, end);
		assertFalse(filter.test(item));
	}

	@Test
	public void dateTimeIsAfterRange() throws ValidationException {
		LocalDateTime start = LocalDateTime.of(2025, 4, 10, 0, 0);
		LocalDateTime end = LocalDateTime.of(2025, 4, 12, 23, 59);
		TestItem item = new TestItem(LocalDateTime.of(2025, 4, 13, 0, 0));
		DateTimeRangeFilter<TestItem> filter = new TestBookingDateTimeRangeFilter(start, end);
		assertFalse(filter.test(item));
	}

	@Test
	public void dateTimeIsSameAsStartTime() throws ValidationException {
		LocalDateTime start = LocalDateTime.of(2025, 4, 10, 10, 0);
		LocalDateTime end = LocalDateTime.of(2025, 4, 12, 23, 59);
		TestItem item = new TestItem(start);
		DateTimeRangeFilter<TestItem> filter = new TestBookingDateTimeRangeFilter(start, end);
		assertTrue(filter.test(item));
	}

	@Test
	public void dateTimeIsSameAsEndTime() throws ValidationException {
		LocalDateTime start = LocalDateTime.of(2025, 4, 10, 10, 0);
		LocalDateTime end = LocalDateTime.of(2025, 4, 12, 23, 59);
		TestItem item = new TestItem(end);
		DateTimeRangeFilter<TestItem> filter = new TestBookingDateTimeRangeFilter(start, end);
		assertTrue(filter.test(item));
	}

	@Test
	public void startTimeIsAfterEnd() {
		LocalDateTime start = LocalDateTime.of(2025, 4, 12, 12, 0);
		LocalDateTime end = LocalDateTime.of(2025, 4, 10, 10, 0);
		ValidationException ex = assertThrows(ValidationException.class, () -> {
			new TestBookingDateTimeRangeFilter(start, end);
		});
		assertEquals("Start datetime must be before or equal to end datetime.", ex.getMessage());
	}

	@Test
	public void whenItemIsNull() throws ValidationException {
		LocalDateTime start = LocalDateTime.of(2025, 4, 10, 0, 0);
		LocalDateTime end = LocalDateTime.of(2025, 4, 12, 23, 59);
		DateTimeRangeFilter<TestItem> filter = new TestBookingDateTimeRangeFilter(start, end);
		assertFalse(filter.test(null));
	}

	@Test
	public void startDateTimeIsNull() {
		LocalDateTime end = LocalDateTime.of(2025, 4, 12, 12, 0);
		ValidationException ex = assertThrows(ValidationException.class, () -> {
			new TestBookingDateTimeRangeFilter(null, end);
		});
		assertEquals("Start or end datetime must not be null.", ex.getMessage());
	}

	@Test
	public void endDateTimeIsNull() {
		LocalDateTime start = LocalDateTime.of(2025, 4, 10, 10, 0);
		ValidationException ex = assertThrows(ValidationException.class, () -> {
			new TestBookingDateTimeRangeFilter(start, null);
		});
		assertEquals("Start or end datetime must not be null.", ex.getMessage());
	}
}
