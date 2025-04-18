package com.pal.taxi.system.booking.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import com.pal.taxi.Taxi;
import com.pal.taxi.Taxi.TaxiResponse;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.TaxiFleetManagement;
import com.pal.taxi.system.TaxiManager;

/**
 * Manages booking requests by processing them sequentially and dispatching to
 * taxis in subsets.
 */
public class BookingRequestsManager {

	/** blocking queue so that we are blocked any requests are available. */
	private final BlockingQueue<BookingRequest> bookingQueue = new LinkedBlockingQueue<>();

	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	private record TaxiBookingRequest(UUID bookingRequestID, UUID taxiUuid) {
	};

	private final Map<TaxiBookingRequest, CompletableFuture<TaxiResponse>> pendingResponses = new ConcurrentHashMap<>();

	private final TaxiManager taxiManager;

	private final TaxiFleetManagement managementSystem;

	private final BookingManagerConfig config;

	public BookingRequestsManager(TaxiFleetManagement managementSystem, TaxiManager taxiManager,
			BookingManagerConfig config) {
		this.managementSystem = managementSystem;
		this.taxiManager = taxiManager;
		this.config = config;
		startProcessingIncomingRequests();
	}

	public void submitBookingRequest(BookingRequest request) {
		bookingQueue.offer(request);
	}

	public void receiveTaxiResponse(UUID bookingId, UUID taxiId, TaxiResponse response) {
		CompletableFuture<TaxiResponse> completableFuture = pendingResponses
				.get(new TaxiBookingRequest(bookingId, taxiId));
		if (null != completableFuture) {
			completableFuture.complete(response);
		}
	}

	private void startProcessingIncomingRequests() {
		executor.submit(() -> {
			while (true) {
				BookingRequest request = bookingQueue.take();
				processBookingRequest(request);
			}
		});
	}

	private void processBookingRequest(BookingRequest request) throws TaxiFleetException {
		Set<UUID> processedTaxis = new HashSet<>();
		long startTime = System.currentTimeMillis();
		long endTime = startTime + config.requestTimeoutMillis();
		while (endTime >= System.currentTimeMillis()) {
			Collection<Taxi> allAvailableTaxis = taxiManager.getAllAvailableTaxis();
			// get the available taxi's list again, because, some taxi's might have been
			// available, during the initial notifications.
			Collection<Taxi> toBeNotifiedTaxis = selectTaxisToNotify(allAvailableTaxis, processedTaxis);
			if (toBeNotifiedTaxis.isEmpty()) {
				managementSystem.noTaxiFound(request);
				return;
			}
			notifyTaxis(toBeNotifiedTaxis, request);
			Taxi selectedTaxi = waitForAnyAcceptanceResponse(request.getUuid(), toBeNotifiedTaxis);
			if (null == selectedTaxi) {
				toBeNotifiedTaxis.stream().map(Taxi::getId).forEach(processedTaxis::add);
			} else {
				// successfully booked, return to the system.
				managementSystem.bookTaxi(request, selectedTaxi);
				return;
			}
		}
		managementSystem.noTaxiFound(request);
	}

	private Collection<Taxi> selectTaxisToNotify(Collection<Taxi> availableTaxis, Set<UUID> alreadyTried) {
		return availableTaxis.stream().filter(taxi -> !alreadyTried.contains(taxi.getId()))
				.limit(config.maxTaxisPerBatch()).collect(Collectors.toSet());
	}

	private void notifyTaxis(Collection<Taxi> taxis, BookingRequest request) {
		managementSystem.notifyTaxis(taxis, request);
	}

	/** assum,es that the taxi we are searching for is part of the Collection. */
	private Taxi getTaxi(Collection<Taxi> taxis, UUID taxiID) {
		return taxis.stream().filter(t -> t.getId().equals(taxiID)).findAny().get();
	}

	private Taxi waitForAnyAcceptanceResponse(UUID bookingId, Collection<Taxi> taxis) {
		Map<TaxiBookingRequest, CompletableFuture<TaxiResponse>> currentBatch = createFutures(bookingId, taxis);

		long startTime = System.currentTimeMillis();
		long endTime = startTime + config.perBatchTimeoutMillis();

		try {
			while (System.currentTimeMillis() <= endTime && !currentBatch.isEmpty()) {
				Collection<TaxiBookingRequest> toBeRemovedRequests = new HashSet<>();
				for (Map.Entry<TaxiBookingRequest, CompletableFuture<TaxiResponse>> entry : currentBatch.entrySet()) {
					TaxiBookingRequest request = entry.getKey();
					CompletableFuture<TaxiResponse> futureResponse = entry.getValue();
					if (futureResponse.isDone()) {
						try {
							if (TaxiResponse.ACCEPTED.equals(futureResponse.get())) {
								return getTaxi(taxis, request.taxiUuid);
							}
							toBeRemovedRequests.add(request);
						} catch (Exception e) {
							// this is okay, we wait for the others to respond.
						}
					}
				}
				toBeRemovedRequests.forEach(currentBatch::remove);
				// sleep after each processing.
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		} finally {
			removedCompletedTaxiRequests(bookingId, taxis);
		}
		return null;
	}

	private void removedCompletedTaxiRequests(UUID bookingId, Collection<Taxi> taxis) {
		for (Taxi taxi : taxis) {
			TaxiBookingRequest request = new TaxiBookingRequest(bookingId, taxi.getId());
			pendingResponses.remove(request);
		}
	}

	/** create a completefuture to track each notification set to the taxi. */
	private Map<TaxiBookingRequest, CompletableFuture<TaxiResponse>> createFutures(UUID bookingId,
			Collection<Taxi> taxis) {
		Map<TaxiBookingRequest, CompletableFuture<TaxiResponse>> currentBatch = new HashMap<>(taxis.size());
		for (Taxi taxi : taxis) {
			TaxiBookingRequest request = new TaxiBookingRequest(bookingId, taxi.getId());
			CompletableFuture<TaxiResponse> future = new CompletableFuture<>();
			currentBatch.put(request, future);
			pendingResponses.put(request, future);
		}
		return currentBatch;
	}

}
