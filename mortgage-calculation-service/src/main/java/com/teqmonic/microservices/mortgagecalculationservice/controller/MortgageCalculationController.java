/**
 * 
 */
package com.teqmonic.microservices.mortgagecalculationservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRequest;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageResponse;
import com.teqmonic.microservices.mortgagecalculationservice.service.MortgageCalculationService;

/**
 * 
 */
@RestController
@RequestMapping("/api/v1")
public class MortgageCalculationController {
	
	Logger logger = LoggerFactory.getLogger(MortgageCalculationController.class);
	
	private MortgageCalculationService mortgageCalculationService;

	public MortgageCalculationController(MortgageCalculationService mortgageCalculationService) {
		this.mortgageCalculationService = mortgageCalculationService;
	}

	@GetMapping("/mortgage-details")
	public ResponseEntity<MortgageResponse> getMortgageDetails(@RequestBody MortgageRequest mortgageRequest) {
		logger.info("Mortgage Request {}", mortgageRequest);
		MortgageResponse mortgageResponse = mortgageCalculationService.getMortgageDetails(mortgageRequest);
		logger.info("Mortgage Response {}", mortgageResponse);
		return new ResponseEntity<MortgageResponse>(mortgageResponse, HttpStatus.OK);
	}
}
