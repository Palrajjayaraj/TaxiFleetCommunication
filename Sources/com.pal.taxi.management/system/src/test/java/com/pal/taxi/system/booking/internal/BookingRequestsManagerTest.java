package com.pal.taxi.system.booking.internal;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pal.taxi.Taxi;
import com.pal.taxi.Taxi.TaxiResponse;
import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.TaxiFleetManagement;
import com.pal.taxi.system.TaxiManager;

/**
 * Unit test for the class {@code BookingRequestsManager}
 */
public class BookingRequestsManagerTest {

	private BookingRequestsManager manager;

	private TaxiManager mockTaxiManager;

	private TaxiFleetManagement mockFleetManagement;

	private BookingRequest testRequest;

	@BeforeEach
	void setUp() throws TaxiFleetException {
		mockTaxiManager = mock(TaxiManager.class);
		mockFleetManagement = mock(TaxiFleetManagement.class);
		BookingManagerConfig config = new BookingManagerConfig(2000, // total timeout (2s)
				1000, // per batch timeout (1s)
				2 // taxis per batch
		);
		manager = new BookingRequestsManager(mockFleetManagement, mockTaxiManager, config);
		Location pickup = new Location(1, 11.0, 77.0, "Bus terminal");
		Location drop = new Location(2, 11.0, 77.0, "Railwau terminal");
		testRequest = BookingRequest.createRequest(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), pickup,
				drop, BookingRequest.Status.PENDING);
	}

	/**
	 * Verifies that a single taxi accepts the booking and is successfully assigned.
	 */
	@Test
	void testBookingAcceptedByTaxi() throws TaxiFleetException {
		UUID taxiId = UUID.randomUUID();
		Taxi taxi = mock(Taxi.class);
		when(taxi.getId()).thenReturn(taxiId);
		when(mockTaxiManager.getAllAvailableTaxis()).thenReturn(List.of(taxi));
		manager.submitBookingRequest(testRequest);
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			manager.receiveTaxiResponse(testRequest.getUuid(), taxiId, TaxiResponse.ACCEPTED);
		}, 200, MILLISECONDS);
		await().atMost(3, SECONDS).untilAsserted(() -> verify(mockFleetManagement).bookTaxi(eq(testRequest), eq(taxi)));
	}

	/**
	 * Verifies that if the first batch of taxis reject, a taxi from the second
	 * batch can still accept.
	 */
	@Test
	void testTaxiAcceptsInSecondBatch() throws TaxiFleetException {
		Taxi taxiReject1 = mock(Taxi.class);
		Taxi taxiReject2 = mock(Taxi.class);
		Taxi acceptingTaxi = mock(Taxi.class);
		when(taxiReject1.getId()).thenReturn(UUID.randomUUID());
		when(taxiReject2.getId()).thenReturn(UUID.randomUUID());
		UUID acceptingTaxiId = UUID.randomUUID();
		when(acceptingTaxi.getId()).thenReturn(acceptingTaxiId);
		when(mockTaxiManager.getAllAvailableTaxis()).thenReturn(List.of(taxiReject1, taxiReject2, acceptingTaxi));
		manager.submitBookingRequest(testRequest);
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			manager.receiveTaxiResponse(testRequest.getUuid(), taxiReject1.getId(), TaxiResponse.REJECTED);
			manager.receiveTaxiResponse(testRequest.getUuid(), taxiReject2.getId(), TaxiResponse.REJECTED);
		}, 200, MILLISECONDS);
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			manager.receiveTaxiResponse(testRequest.getUuid(), acceptingTaxiId, TaxiResponse.ACCEPTED);
		}, 1100, MILLISECONDS);
		await().atMost(3, SECONDS)
				.untilAsserted(() -> verify(mockFleetManagement).bookTaxi(eq(testRequest), eq(acceptingTaxi)));
		verify(mockFleetManagement, never()).noTaxiFound(any());
	}

	/**
	 * Verifies that if all taxis reject the booking and no one accepts, the system
	 * triggers noTaxiFound.
	 */
	@Test
	void testAllTaxisReject_AndTimeoutOccurs() throws TaxiFleetException {
		Taxi taxiReject1 = mock(Taxi.class);
		Taxi taxiReject2 = mock(Taxi.class);
		when(taxiReject1.getId()).thenReturn(UUID.randomUUID());
		when(taxiReject2.getId()).thenReturn(UUID.randomUUID());
		when(mockTaxiManager.getAllAvailableTaxis()).thenReturn(List.of(taxiReject1, taxiReject2));
		manager.submitBookingRequest(testRequest);
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			manager.receiveTaxiResponse(testRequest.getUuid(), taxiReject1.getId(), TaxiResponse.REJECTED);
			manager.receiveTaxiResponse(testRequest.getUuid(), taxiReject2.getId(), TaxiResponse.REJECTED);
		}, 200, MILLISECONDS);
		await().atMost(5, SECONDS).untilAsserted(() -> verify(mockFleetManagement).noTaxiFound(eq(testRequest)));
		verify(mockFleetManagement, never()).bookTaxi(any(), any());
	}

	/**
	 * Verifies that if multiple taxis accept, only the first one to respond is
	 * selected for booking.
	 */
	@Test
	void testMultipleTaxisAccept_firstAcceptedIsUsed() throws TaxiFleetException {
		Taxi taxi1 = mock(Taxi.class);
		Taxi taxi2 = mock(Taxi.class);
		UUID taxiId1 = UUID.randomUUID();
		UUID taxiId2 = UUID.randomUUID();
		when(taxi1.getId()).thenReturn(taxiId1);
		when(taxi2.getId()).thenReturn(taxiId2);
		when(mockTaxiManager.getAllAvailableTaxis()).thenReturn(List.of(taxi1, taxi2));
		manager.submitBookingRequest(testRequest);
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			manager.receiveTaxiResponse(testRequest.getUuid(), taxiId1, TaxiResponse.ACCEPTED);
		}, 200, MILLISECONDS);
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			manager.receiveTaxiResponse(testRequest.getUuid(), taxiId2, TaxiResponse.ACCEPTED);
		}, 600, MILLISECONDS);
		await().atMost(3, SECONDS).untilAsserted(() -> verify(mockFleetManagement).bookTaxi(eq(testRequest), eq(taxi1)));
		verify(mockFleetManagement, never()).bookTaxi(eq(testRequest), eq(taxi2));
	}

	/**
	 * Verifies that if no taxis are available, the system immediately triggers
	 * noTaxiFound.
	 */
	@Test
	void testNoAvailableTaxis_noTaxiFoundImmediately() throws TaxiFleetException {
		when(mockTaxiManager.getAllAvailableTaxis()).thenReturn(List.of());
		manager.submitBookingRequest(testRequest);
		await().atMost(3, SECONDS).untilAsserted(() -> verify(mockFleetManagement).noTaxiFound(eq(testRequest)));
		verify(mockFleetManagement, never()).bookTaxi(any(), any());
	}

	/**
	 * Verifies that the system handles fewer taxis than batch size and still
	 * functions properly.
	 */
	@Test
	void testLessThanBatchSizeTaxis_availableAndAccept() throws TaxiFleetException {
		Taxi taxi1 = mock(Taxi.class);
		UUID taxiId = UUID.randomUUID();
		when(taxi1.getId()).thenReturn(taxiId);
		when(mockTaxiManager.getAllAvailableTaxis()).thenReturn(List.of(taxi1));
		manager.submitBookingRequest(testRequest);
		Executors.newSingleThreadScheduledExecutor().schedule(
				() -> manager.receiveTaxiResponse(testRequest.getUuid(), taxiId, TaxiResponse.ACCEPTED), 300,
				MILLISECONDS);
		await().atMost(3, SECONDS).untilAsserted(() -> verify(mockFleetManagement).bookTaxi(eq(testRequest), eq(taxi1)));
		verify(mockFleetManagement, never()).noTaxiFound(any());
	}

	/**
	 * Verifies that when more taxis than batch size are available, and the first
	 * batch has an acceptance, second batch is not processed.
	 */
	@Test
	void testMoreThanBatchSizeTaxis_acceptInFirstBatchOnly() throws TaxiFleetException {
		Taxi taxi1 = mock(Taxi.class);
		UUID taxiId1 = UUID.randomUUID();
		when(taxi1.getId()).thenReturn(taxiId1);
		Taxi taxi2 = mock(Taxi.class);
		UUID taxiId2 = UUID.randomUUID();
		when(taxi2.getId()).thenReturn(taxiId2);
		Taxi taxi3 = mock(Taxi.class);
		UUID taxiId3 = UUID.randomUUID();
		when(taxi3.getId()).thenReturn(taxiId3);
		Taxi taxi4 = mock(Taxi.class);
		UUID taxiId4 = UUID.randomUUID();
		when(taxi4.getId()).thenReturn(taxiId4);
		Taxi taxi5 = mock(Taxi.class);
		UUID taxiId5 = UUID.randomUUID();
		when(taxi5.getId()).thenReturn(taxiId5);
		List<Taxi> allTaxis = List.of(taxi1, taxi2, taxi3, taxi4, taxi5);
		when(mockTaxiManager.getAllAvailableTaxis()).thenReturn(allTaxis);
		manager.submitBookingRequest(testRequest);
		Executors.newSingleThreadScheduledExecutor().schedule(
				() -> manager.receiveTaxiResponse(testRequest.getUuid(), taxiId1, TaxiResponse.ACCEPTED), 200,
				MILLISECONDS);
		await().atMost(3, SECONDS).untilAsserted(() -> verify(mockFleetManagement).bookTaxi(eq(testRequest), eq(taxi1)));
		verify(mockFleetManagement, never()).bookTaxi(eq(testRequest), eq(taxi3));
		verify(mockFleetManagement, never()).bookTaxi(eq(testRequest), eq(taxi4));
		verify(mockFleetManagement, never()).bookTaxi(eq(testRequest), eq(taxi5));
	}

	/** Verifies that ACCEPTED responses arriving after timeout are ignored. */
	@Test
	void testLateAcceptanceIgnoredAfterTimeout() throws TaxiFleetException {
		Taxi taxi = mock(Taxi.class);
		UUID taxiId = UUID.randomUUID();
		when(taxi.getId()).thenReturn(taxiId);
		when(mockTaxiManager.getAllAvailableTaxis()).thenReturn(List.of(taxi));
		manager.submitBookingRequest(testRequest);
		Executors.newSingleThreadScheduledExecutor().schedule(
				() -> manager.receiveTaxiResponse(testRequest.getUuid(), taxiId, TaxiResponse.ACCEPTED), 2500,
				MILLISECONDS);
		await().atMost(5, SECONDS).untilAsserted(() -> verify(mockFleetManagement).noTaxiFound(eq(testRequest)));
		verify(mockFleetManagement, never()).bookTaxi(any(), any());
	}

	/**
	 * Verifies that multiple requests are processed independently in sequence.
	 */
	@Test
	void testMultipleBookingRequestsProcessedIndependently() throws TaxiFleetException {
		Taxi taxi1 = mock(Taxi.class);
		Taxi taxi2 = mock(Taxi.class);
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		when(taxi1.getId()).thenReturn(id1);
		when(taxi2.getId()).thenReturn(id2);
		when(mockTaxiManager.getAllAvailableTaxis()).thenReturn(List.of(taxi1)) // for first request
				.thenReturn(List.of(taxi2)); // for second request
		BookingRequest request2 = BookingRequest.createRequest(UUID.randomUUID(), UUID.randomUUID(),
				LocalDateTime.now(), testRequest.getPickupLocation(), testRequest.getDropoffLocation(),
				BookingRequest.Status.PENDING);
		manager.submitBookingRequest(testRequest);
		manager.submitBookingRequest(request2);
		// responses for both
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			manager.receiveTaxiResponse(testRequest.getUuid(), id1, TaxiResponse.ACCEPTED);
		}, 200, MILLISECONDS);
		await().atMost(5, SECONDS).untilAsserted(() -> {
			verify(mockFleetManagement).bookTaxi(eq(testRequest), eq(taxi1));
		});
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			manager.receiveTaxiResponse(request2.getUuid(), id2, TaxiResponse.ACCEPTED);
		}, 200, MILLISECONDS);
		// Verify both bookings were processed successfully
		await().atMost(5, SECONDS).untilAsserted(() -> {
			verify(mockFleetManagement).bookTaxi(eq(request2), eq(taxi2));
		});
	}

}