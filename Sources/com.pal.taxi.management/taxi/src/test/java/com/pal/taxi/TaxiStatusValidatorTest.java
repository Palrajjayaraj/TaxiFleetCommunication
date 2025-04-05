package com.pal.taxi;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.pal.taxi.Taxi.TaxiStatus;
import com.pal.taxi.common.validation.ValidationStatus;

/**
 * Junit for {@link TaxiStatusValidator}
 */
@RunWith(Parameterized.class)
public class TaxiStatusValidatorTest {

	private final TaxiStatus currentStatus;

	private final TaxiStatus newStatus;

	private final boolean expectedValidTransition;

	private final String validationErrorMessage;

	public TaxiStatusValidatorTest(TaxiStatus currentStatus, TaxiStatus newStatus, boolean expectedValidTransition,
			String validationErrorMessage) {
		this.currentStatus = currentStatus;
		this.newStatus = newStatus;
		this.expectedValidTransition = expectedValidTransition;
		this.validationErrorMessage = validationErrorMessage;
	}

	@Parameterized.Parameters(name = "From {0} to {1} => valid={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				// From AVAILABLE
				{ TaxiStatus.AVAILABLE, TaxiStatus.AVAILABLE, true, "" },
				{ TaxiStatus.AVAILABLE, TaxiStatus.BOOKED, true, "" },
				{ TaxiStatus.AVAILABLE, TaxiStatus.RIDING, false,
						"Cannot set riding state, from available and must move through Booked." },
				{ TaxiStatus.AVAILABLE, TaxiStatus.OFFLINE, true, "" },
				// From BOOKED
				{ TaxiStatus.BOOKED, TaxiStatus.BOOKED, true, "" }, { TaxiStatus.BOOKED, TaxiStatus.RIDING, true, "" },
				{ TaxiStatus.BOOKED, TaxiStatus.AVAILABLE, true, "" },
				{ TaxiStatus.BOOKED, TaxiStatus.OFFLINE, false,
						"A booking has been assigned, moving to offline is not allowed." },
				// From RIDING
				{ TaxiStatus.RIDING, TaxiStatus.RIDING, false,
						"Moving from Riding to available is allowed. It is not allowed to move to any other status." },
				{ TaxiStatus.RIDING, TaxiStatus.AVAILABLE, true, "" },
				{ TaxiStatus.RIDING, TaxiStatus.BOOKED, false,
						"Moving from Riding to available is allowed. It is not allowed to move to any other status." },
				{ TaxiStatus.RIDING, TaxiStatus.OFFLINE, false,
						"Moving from Riding to available is allowed. It is not allowed to move to any other status." },
				// From OFFLINE
				{ TaxiStatus.OFFLINE, TaxiStatus.OFFLINE, true, "" },
				{ TaxiStatus.OFFLINE, TaxiStatus.AVAILABLE, true, "" },
				{ TaxiStatus.OFFLINE, TaxiStatus.RIDING, false,
						"Moving from offline to available is allowed. It is not allowed to move to any other status." },
				{ TaxiStatus.OFFLINE, TaxiStatus.BOOKED, false,
						"Moving from offline to available is allowed. It is not allowed to move to any other status." } });
	}

	@Test
	public void testValidateTransition() {
		TaxiStatusValidator validator = new TaxiStatusValidator(currentStatus, newStatus);
		ValidationStatus result = validator.validate();
		assertEquals("Expected validity mismatch for transition " + currentStatus + " → " + newStatus,
				expectedValidTransition, result.isOk());
		if (!expectedValidTransition) {
			assertEquals("Expected error message mismatch for transition " + currentStatus + " → " + newStatus,
					validationErrorMessage, result.message());
		}
	}
}