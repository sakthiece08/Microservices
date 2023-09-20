package com.teqmonic.microservices.mortgagerateservice.errorhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.teqmonic.microservices.mortgagerateservice.errorhandler.model.ApiErrorResponse;
import com.teqmonic.microservices.mortgagerateservice.errorhandler.model.ResponseCodes;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder().errorCode(ex.getResponseCodes().getErrorCode())
				.errorMessage(ex.getMessage()).build();
		log.error(ex.getMessage(), ex.getCause());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, ex.getResponseCodes().getHttpStatus());
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiErrorResponse> handleResourceNotFoundEx(RuntimeException ex) {
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder() .errorCode(ResponseCodes.INTERNAL_SERVER_ERROR.getErrorCode())
				.errorMessage(ResponseCodes.INTERNAL_SERVER_ERROR.getErrorReason()).build();
		log.error(ex.getMessage(), ex.getCause());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, ResponseCodes.INTERNAL_SERVER_ERROR.getHttpStatus());
	}

}
