package com.teqmonic.microservices.mortgagecalculationservice.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teqmonic.microservices.mortgagecalculationservice.bean.ExchangeRateResponse;
import com.teqmonic.microservices.mortgagecalculationservice.service.ExchangeRateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExchangeRateController {
	
	Logger logger = LoggerFactory.getLogger(ExchangeRateController.class);
	
	private final ExchangeRateService exchangeRateService;
	
	@GetMapping("/exchangerate")
	public ResponseEntity<ExchangeRateResponse> getExchangeRate(@RequestHeader(name = "x-api-Key") Optional<String> xApiKey, 
			@RequestParam(name = "pair", required = true) String pair) {
		ExchangeRateResponse response = exchangeRateService.getExchangeRate(xApiKey, pair);
		logger.info("ExchangeRateResponse {}", response);
		return new ResponseEntity<ExchangeRateResponse>(response, HttpStatus.OK);
	}

}
