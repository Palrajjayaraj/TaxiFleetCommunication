package com.pal.taxi.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pal.taxi.Taxi;
import com.pal.taxi.common.validation.ValidationException;
import com.pal.taxi.web.internal.payload.TaxiMapper;
import com.pal.taxi.web.internal.payload.TaxiPayload;
import com.pal.taxi.web.service.TaxiService;

@RestController
@RequestMapping("/taxi")
public class TaxiController {

	private final TaxiService service;

	public TaxiController(TaxiService service) {
		this.service = service;
	}

	/**
	 * Updates the latest state of the taxi. <br>
	 * The state is contained within the taxi itself.
	 * 
	 * @param payload
	 * @return the status, whether the update of the status is successful or not.
	 * @throws ValidationException
	 */
	@PostMapping("/updateState")
	public ResponseEntity<HttpStatus> updateState(@RequestBody TaxiPayload payload) {
		return SafeRunner.run(() -> {
			Taxi taxi = TaxiMapper.INSTANCE.toTaxi(payload);
			service.updateState(taxi);
		});
	}

}
