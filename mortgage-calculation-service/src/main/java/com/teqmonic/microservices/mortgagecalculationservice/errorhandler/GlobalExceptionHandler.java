package com.teqmonic.microservices.mortgagecalculationservice.errorhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.ServletWebRequest;

import com.teqmonic.microservices.mortgagecalculationservice.errorhandler.model.ApiErrorResponse;
import com.teqmonic.microservices.mortgagecalculationservice.errorhandler.model.ResponseCodes;

import feign.FeignException.FeignClientException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, ServletWebRequest request) {
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder().errorCode(ex.getResponseCodes().getErrorCode())
				.errorMessage(ex.getMessage()) 
				.path(request.getRequest().getRequestURI())
				.status(ex.getResponseCodes().getStatus())
				.build();
		log.error(ex.getMessage(), ex.getCause());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, ex.getResponseCodes().getHttpStatus());
	}
	
	// Covers 4xx exceptions.
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ApiErrorResponse> handleRestClientEx(HttpClientErrorException ex, ServletWebRequest request) {
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder() .errorCode(ResponseCodes.BAD_REQUEST.getErrorCode())
				.errorMessage(ResponseCodes.BAD_REQUEST.getErrorReason())
				.path(request.getRequest().getRequestURI())
				.status(ResponseCodes.BAD_REQUEST.getStatus())
				.additionalDetails(ex.getResponseBodyAsString().replace("\\\"", ""))
				.build();
		log.error(ex.getMessage(), ex.getCause());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, ResponseCodes.BAD_REQUEST.getHttpStatus());
	}
	
	@ExceptionHandler(FeignClientException.class)
	public ResponseEntity<ApiErrorResponse> handleFeignClientEx(FeignClientException ex, ServletWebRequest request) {
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder() .errorCode(ResponseCodes.BAD_REQUEST.getErrorCode())
				.errorMessage(ResponseCodes.BAD_REQUEST.getErrorReason())
				.path(request.getRequest().getRequestURI())
				.status(ResponseCodes.BAD_REQUEST.getStatus())
				.additionalDetails(ex.getMessage().replace("\\\"", ""))
				.build();
		log.error(ex.getMessage(), ex.getCause());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, ResponseCodes.BAD_REQUEST.getHttpStatus());
	}
	
	@ExceptionHandler(RestClientResponseException.class)
	public ResponseEntity<ApiErrorResponse> handleRestClientEx(RestClientResponseException ex, ServletWebRequest request) {
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder() .errorCode(ResponseCodes.INTERNAL_SERVER_ERROR.getErrorCode())
				.errorMessage(ResponseCodes.INTERNAL_SERVER_ERROR.getErrorReason())
				.path(request.getRequest().getRequestURI())
				.status(ResponseCodes.INTERNAL_SERVER_ERROR.getStatus())
				.additionalDetails(ex.getResponseBodyAsString().replace("\\\"", ""))
				.build();
		log.error(ex.getMessage(), ex.getCause());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, ResponseCodes.INTERNAL_SERVER_ERROR.getHttpStatus());
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiErrorResponse> handleResourceNotFoundEx(RuntimeException ex, ServletWebRequest request) {
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder() .errorCode(ResponseCodes.INTERNAL_SERVER_ERROR.getErrorCode())
				.errorMessage(ResponseCodes.INTERNAL_SERVER_ERROR.getErrorReason())
				.path(request.getRequest().getRequestURI())
				.status(ResponseCodes.INTERNAL_SERVER_ERROR.getStatus())
				.build();
		log.error(ex.getMessage(), ex.getCause());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, ResponseCodes.INTERNAL_SERVER_ERROR.getHttpStatus());
	}

}
