package com.teqmonic.microservices.mortgagecalculationservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageLenderResponse;
import com.teqmonic.microservices.mortgagecalculationservice.service.MortgageLendersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MortgageLendersController {
	
	Logger logger = LoggerFactory.getLogger(MortgageLendersController.class);
	
	private final MortgageLendersService mortgageLendersService;
	
	@GetMapping("/mortgage-lenders")
	public ResponseEntity<MortgageLenderResponse> getMortgageLenders(){
		MortgageLenderResponse response = mortgageLendersService.getLenders();
		logger.info("MortgageLenderResponse Response {}", response);
		return new ResponseEntity<MortgageLenderResponse>(response, HttpStatus.OK);
	}

}
