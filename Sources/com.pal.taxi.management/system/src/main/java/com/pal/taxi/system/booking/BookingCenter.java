package com.pal.taxi.system.booking;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BookingCenter {

    private final Map<UUID, Booking> bookings = new ConcurrentHashMap<>();

    public void publishBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking(
                bookingRequest.getUserId(),
                bookingRequest.getPickupLocation(),
                bookingRequest.getDropLocation(),
                bookingRequest.getRequestedTime()
        );
        bookings.put(booking.getId(), booking);
        System.out.println("Booking published: " + booking.getId());
    }

    public Collection<Booking> queryBooking(Collection<Filter<Booking>> filters) {
        return bookings.values().stream()
                .filter(booking -> filters.stream().allMatch(f -> f.apply(booking)))
                .collect(Collectors.toList());
    }

    public void handleTaxiResponse(BookingResponse response) {
        Booking booking = bookings.get(response.getBookingId());
        if (booking != null) {
            booking.setStatus(response.getStatus());
            booking.setAssignedTaxiId(response.getTaxiId());
            booking.setUpdatedTime(LocalDateTime.now());
            System.out.println("Booking updated: " + booking.getId() + " -> " + booking.getStatus());
        } else {
            System.err.println("Booking not found for response: " + response.getBookingId());
        }
    }
}
