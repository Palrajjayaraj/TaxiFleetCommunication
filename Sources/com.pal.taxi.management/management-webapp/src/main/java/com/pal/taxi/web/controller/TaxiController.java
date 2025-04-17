package com.pal.taxi.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.Location;

@RestController
@RequestMapping("/taxi")
public class TaxiController {

	@PostMapping("/updateLocation")
	public void updateLocation(Taxi taxi, Location location) {
		
	}
	
}
