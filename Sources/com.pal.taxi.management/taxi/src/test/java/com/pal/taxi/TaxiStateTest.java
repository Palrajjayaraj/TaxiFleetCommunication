package com.pal.taxi;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.pal.taxi.Taxi.TaxiStatus;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.validation.ValidationException;

/**
 * JUnit for {@link TaxiState}
 */
@RunWith(Parameterized.class)
public class TaxiStateTest {

	private final TaxiStatus currentStatus;

	private final TaxiStatus newStatus;

	private final boolean shouldPass;

	public TaxiStateTest(TaxiStatus currentStatus, TaxiStatus newStatus, boolean shouldPass) {
		this.currentStatus = currentStatus;
		this.newStatus = newStatus;
		this.shouldPass = shouldPass;
	}

	@Parameterized.Parameters(name = "From {0} to {1} shouldPass={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { TaxiStatus.AVAILABLE, TaxiStatus.BOOKED, true },
				{ TaxiStatus.AVAILABLE, TaxiStatus.RIDING, false }, { TaxiStatus.RIDING, TaxiStatus.AVAILABLE, true },
				{ TaxiStatus.OFFLINE, TaxiStatus.BOOKED, false }, { TaxiStatus.BOOKED, TaxiStatus.OFFLINE, false },
				{ TaxiStatus.BOOKED, TaxiStatus.RIDING, true }, });
	}

	@Test
	public void testSetStatus() throws Exception {
		TaxiState state = new TaxiState(currentStatus, new Location(1, 12.0, 77.0, "Bus terminal"));
		try {
			state.setStatus(newStatus);
			if (!shouldPass) {
				fail("Expected ValidationException.");
			}
		} catch (ValidationException e) {
			if (shouldPass) {
				fail("Expected successful Setting of status: " + e.getMessage());
			}
		}
	}
}
