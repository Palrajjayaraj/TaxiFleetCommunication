package com.pal.taxi.web.sse;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.notification.IBookingRequestNotifier;
import com.pal.taxi.web.service.TaxiFleetManagementServicesFacade;

/**
 * The notifier, which send the requests to the given available taxis.
 * 
 * @author Palraj
 */
@Component
public class SseBookingRequestNotifier implements IBookingRequestNotifier {

	private final TaxiSSEService sseService;

	private final TaxiFleetManagementServicesFacade facade;

	public SseBookingRequestNotifier(TaxiSSEService sseService, TaxiFleetManagementServicesFacade facade) {
		this.sseService = sseService;
		this.facade = facade;
	}

	@Override
	public void notifyTaxis(Collection<Taxi> taxis, BookingRequest bookingRequest) {
		for (Taxi taxi : taxis) {
			sseService.sendToTaxi(taxi.getId(), bookingRequest);
		}
	}

	@PostConstruct
	public void register() {
		facade.getManagement().addBookingRequestNotifier(this);
	}

}
