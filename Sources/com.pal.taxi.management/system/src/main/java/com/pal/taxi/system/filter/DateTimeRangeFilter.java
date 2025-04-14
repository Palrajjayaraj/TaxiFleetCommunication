package com.pal.taxi.system.filter;

import java.time.LocalDateTime;

import com.pal.taxi.common.validation.ValidationException;

/**
 * A filter which can filter only the records which pages the given date time
 * range.
 * 
 * @author Palraj
 */
public abstract class DateTimeRangeFilter<T> implements IFilter<T> {

	private final LocalDateTime startDateTime;

	private final LocalDateTime endDateTime;

	/**
	 * Creates the date time range filter
	 * 
	 * @param startDateTime The start date time. Must not be {@code null}
	 * @param endDateTime   the end date time. Must not be {@code null}
	 * @throws ValidationException if either of the arguments are null or start time
	 *                             is after end time.
	 */
	public DateTimeRangeFilter(LocalDateTime startDateTime, LocalDateTime endDateTime) throws ValidationException {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		assertValidArguments();
	}

	protected abstract LocalDateTime getDateTime(T item);

	private void assertValidArguments() throws ValidationException {
		if (startDateTime == null || endDateTime == null) {
			throw new ValidationException("Start or end datetime must not be null.", null);
		}
		if (startDateTime.isAfter(endDateTime)) {
			throw new ValidationException("Start datetime must be before or equal to end datetime.", null);
		}
	}

	@Override
	public boolean test(T item) {
		return (null != item) && (getDateTime(item).isEqual(startDateTime) || getDateTime(item).isAfter(startDateTime))
				&& (getDateTime(item).isEqual(endDateTime) || getDateTime(item).isBefore(endDateTime));
	}

}
