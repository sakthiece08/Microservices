package com.teqmonic.microservices.mortgagecalculationservice.errorhandler.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodes {

	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, Severity.ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "ERR_MORT_DET_100", "500"),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, Severity.ERROR, "The requested resource not found", "ERR_MORT_DET_101", "404");

	private HttpStatus httpStatus;
	private Severity severity;
	private String errorReason;
	private String errorCode;
	private String status;

}
