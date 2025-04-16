package com.pal.taxi.web.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pal.taxi.common.Location;
import com.pal.taxi.common.TaxiFleetException;
import com.pal.taxi.common.ThrowingSupplier;
import com.pal.taxi.user.User;
import com.pal.taxi.web.service.CommonService;

@RestController
@RequestMapping("/common")
public class CommonController {

	private final CommonService commonService;

	public CommonController(CommonService locationService) {
		this.commonService = locationService;
	}

	@GetMapping("/locations")
	public ResponseEntity<Collection<Location>> getLocations() {
		return wrapAroundEntity(() -> commonService.getAllLocations());
	}

	@GetMapping("/users")
	public ResponseEntity<Collection<User>> getUsers() {
		return wrapAroundEntity(() -> commonService.getAllUsers());
	}

	private <T> ResponseEntity<T> wrapAroundEntity(ThrowingSupplier<T> supplier) {
		try {
			return ResponseEntity.ok(supplier.get());
		} catch (TaxiFleetException e) {
			// not required to set the stack trace to the front end.
			return ResponseEntity.internalServerError().build();
		}
	}
}
