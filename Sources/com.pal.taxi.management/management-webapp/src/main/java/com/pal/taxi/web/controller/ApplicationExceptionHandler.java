package com.pal.taxi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.pal.taxi.common.TaxiFleetException;

/**
 * exception handler for this whole web application
 * 
 * @author Palraj
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

	@ExceptionHandler(TaxiFleetException.class)
	public ResponseEntity<ErrorResponse> handleTaxiFleetException(TaxiFleetException ex, WebRequest request) {
		logger.error("Business error: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(new ErrorResponse("BUSINESS_ERROR", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
		logger.error("Unexpected error: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static class ErrorResponse {
		private String code;
		private String message;

		public ErrorResponse(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}
