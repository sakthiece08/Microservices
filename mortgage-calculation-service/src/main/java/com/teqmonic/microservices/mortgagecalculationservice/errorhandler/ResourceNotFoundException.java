package com.teqmonic.microservices.mortgagecalculationservice.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.teqmonic.microservices.mortgagecalculationservice.errorhandler.model.ResponseCodes;

import lombok.Getter;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class ResourceNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ResponseCodes responseCodes;
	private String message;

	public ResourceNotFoundException(ResponseCodes responseCodes, String message) {
		this.responseCodes = responseCodes;
		this.message = message;
	}	

}
