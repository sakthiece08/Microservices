package com.teqmonic.microservices.mortgagerateservice.errorhandler.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiErrorResponse {

	private String errorCode;
	private String errorMessage;

}
