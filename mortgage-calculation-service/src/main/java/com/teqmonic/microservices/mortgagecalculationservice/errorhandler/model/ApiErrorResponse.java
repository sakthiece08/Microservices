package com.teqmonic.microservices.mortgagecalculationservice.errorhandler.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiErrorResponse {

	private String errorCode;
	private String errorMessage;
	@Builder.Default
	private LocalDateTime timeStamp = LocalDateTime.now();
	private String path;
	private String status;
	private String additionalDetails;

}
