package com.teqmonic.microservices.mortgagecalculationservice;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRequest;
import com.teqmonic.microservices.mortgagecalculationservice.service.MortgageCalculationService;

@ExtendWith(SpringExtension.class)
public class MortgageCalculationServiceTest {

	static MortgageCalculationService mortgageCalculationService = null;
	
	//private MortgageRequest mortgageRequest = new MortgageRequest(); 

	@BeforeAll
	public static void setUp() {
		//mortgageCalculationService = new MortgageCalculationService();
	}
	
	@Test
	public void getMortgageTest() {
		//Assert.notNull(mortgageCalculationService.getMortgageDetails(mortgageRequest), "Response must not be null");
	}
}
