package com.pal.taxi.web.sse;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.booking.Booking;
import com.pal.taxi.common.booking.BookingRequest;
import com.pal.taxi.system.notification.IBookingListener;
import com.pal.taxi.user.User;
import com.pal.taxi.web.service.TaxiFleetManagementServicesFacade;

@Component
public class SSEBookingNotifier implements IBookingListener {

	private final TaxiSSEService taxiSseService;

	private final UserSSEService userSseService;

	private final TaxiFleetManagementServicesFacade facade;

	public SSEBookingNotifier(TaxiSSEService sseService, UserSSEService userService,
			TaxiFleetManagementServicesFacade facade) {
		this.taxiSseService = sseService;
		this.userSseService = userService;
		this.facade = facade;
	}

	@Override
	public void accepted(BookingRequest request, User user, Booking booking, Taxi taxi) {
		taxiSseService.sendConfirmationBooking(taxi.getId(), booking);
		userSseService.sendConfirmationBooking(user.getUuid(), booking);
	}

	@Override
	public void noTaxisFound(BookingRequest request, User user) {

	}

	@PostConstruct
	public void register() {
		facade.getManagement().addBookingListener(this);
	}

}
