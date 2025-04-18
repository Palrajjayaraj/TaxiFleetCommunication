package com.pal.taxi.persistence.internal;

/**
 * single registry for all the repositories.
 * 
 * @author Palraj
 */
public class RepositoryRegistry {

	private static BookingRepository bookingRepository = BookingRepository.getInstance();

	private static BookingRequestRepository bookingRequestRepository = BookingRequestRepository.getInstance();

	private static TaxiRepository taxiRepository = TaxiRepository.getInstance();

	private static UserRepository userRepository = UserRepository.getInstance();

	private static LocationRepository locationRepository = LocationRepository.getInstance();

	public static BookingRepository getBookingRepository() {
		return bookingRepository;
	}

	public static BookingRequestRepository getBookingRequestRepository() {
		return bookingRequestRepository;
	}

	public static TaxiRepository getTaxiRepository() {
		return taxiRepository;
	}

	public static UserRepository getUserRepository() {
		return userRepository;
	}

	public static LocationRepository getLocationRepository() {
		return locationRepository;
	}

	// Setters for testability
	public static void setBookingRepository(BookingRepository repo) {
		bookingRepository = repo;
	}

	public static void setBookingRequestRepository(BookingRequestRepository repo) {
		bookingRequestRepository = repo;
	}

	public static void setTaxiRepository(TaxiRepository repo) {
		taxiRepository = repo;
	}

	public static void setUserRepository(UserRepository repo) {
		userRepository = repo;
	}

	public static void setLocationRepository(LocationRepository repo) {
		locationRepository = repo;
	}

	public static void resetDefaults() {
		bookingRepository = BookingRepository.getInstance();
		bookingRequestRepository = BookingRequestRepository.getInstance();
		taxiRepository = TaxiRepository.getInstance();
		userRepository = UserRepository.getInstance();
		locationRepository = LocationRepository.getInstance();
	}
}
