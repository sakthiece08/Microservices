package com.teqmonic.microservices.mortgagerateservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teqmonic.microservices.mortgagerateservice.bean.MortgageRatesResponseData;
import com.teqmonic.microservices.mortgagerateservice.service.MortgageRateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
//@Log4j2
public class MortgageRateController {
	
	private final MortgageRateService mortgageRateService;
	
	@GetMapping("/mortgage-rates")
	public ResponseEntity<MortgageRatesResponseData> getAllMortgageRates() {
		MortgageRatesResponseData response = mortgageRateService.getAllMortgageRates();
		log.info("MortgageRatesResponseData {}", response);
		if(response.getMortgageRates().isEmpty()) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/mortgage-rates/{profileCode}")
	public ResponseEntity<MortgageRatesResponseData> getMortgageRate(@PathVariable String profileCode) {
		log.info("profile code {}", profileCode);
		MortgageRatesResponseData response = mortgageRateService.getMortgageRateByProfileCode(profileCode);
		log.info("MortgageRatesResponseData {}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
