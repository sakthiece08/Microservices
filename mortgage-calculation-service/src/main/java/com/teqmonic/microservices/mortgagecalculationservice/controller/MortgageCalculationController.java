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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRequest;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageResponse;
import com.teqmonic.microservices.mortgagecalculationservice.service.MortgageCalculationService;

import lombok.RequiredArgsConstructor;

/**
 * 
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MortgageCalculationController {
	
	Logger logger = LoggerFactory.getLogger(MortgageCalculationController.class);
	
	private final MortgageCalculationService mortgageCalculationService;

	@GetMapping("/mortgage-details")
	public ResponseEntity<MortgageResponse> getMortgageDetails(@RequestHeader(name = "x-isMockResponse", required = false) boolean isMockResponse, @RequestBody MortgageRequest mortgageRequest) {
		logger.info("Mortgage Request {}", mortgageRequest);
		MortgageResponse mortgageResponse = mortgageCalculationService.getMortgageDetails(isMockResponse, false, mortgageRequest);
		
		logger.info("Mortgage Response {}", mortgageResponse);
		return new ResponseEntity<MortgageResponse>(mortgageResponse, HttpStatus.OK);
	}
	
	@GetMapping("/mortgage-details-feign")
	public ResponseEntity<MortgageResponse> getMortgageDetailsFeign(@RequestHeader(name = "x-isMockResponse", required = false) boolean isMockResponse, @RequestBody MortgageRequest mortgageRequest) {
		logger.info("Mortgage Request {}", mortgageRequest);
		MortgageResponse mortgageResponse = mortgageCalculationService.getMortgageDetails(isMockResponse, true, mortgageRequest);
		
		logger.info("Mortgage Response {}", mortgageResponse);
		return new ResponseEntity<MortgageResponse>(mortgageResponse, HttpStatus.OK);
	}
	
	@GetMapping("/mortgage-details-groupby-profilecode")
	public ResponseEntity<MortgageResponse> getMortgageDetailsGroupByProfile(@RequestHeader(name = "x-isMockResponse", required = false) boolean isMockResponse, @RequestBody MortgageRequest mortgageRequest) {
		logger.info("Mortgage Request {}", mortgageRequest);
		MortgageResponse mortgageResponse = mortgageCalculationService.getMortgageDetailsGroupByProfileCode(isMockResponse, false, mortgageRequest);
		
		logger.info("Mortgage Response {}", mortgageResponse);
		return new ResponseEntity<MortgageResponse>(mortgageResponse, HttpStatus.OK);
	}
}
