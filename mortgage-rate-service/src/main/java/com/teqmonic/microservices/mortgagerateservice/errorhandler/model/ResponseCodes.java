package com.teqmonic.microservices.mortgagerateservice.errorhandler.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodes {

	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, Severity.ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "ERR_MORT_100"),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, Severity.ERROR, "The requested resource not found", "ERR_MORT_101");

	private HttpStatus httpStatus;
	private Severity severity;
	private String errorReason;
	private String errorCode;

}
