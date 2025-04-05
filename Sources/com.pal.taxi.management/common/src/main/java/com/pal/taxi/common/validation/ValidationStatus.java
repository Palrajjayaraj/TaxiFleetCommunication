package com.pal.taxi.common.validation;

public record ValidationStatus(Severity severity, String message, Throwable throwable) {

	/** Represents a validation status state */
	public static final ValidationStatus OK_STATUS = new ValidationStatus(Severity.OK, "", null);

	/** Whether the status is an ok status. */
	public boolean isOk() {
		return Severity.OK.equals(severity);
	}

	enum Severity {
		/** validation failed and must be handled by the application. */
		ERROR,
		/** validatoin failed and application may or may not take action. */
		WARNING,
		/** validation is successful. */
		OK;
	}

	public static ValidationStatus createErrorStatus(String message, Throwable throwable) {
		return new ValidationStatus(Severity.ERROR, message, throwable);
	}

}
