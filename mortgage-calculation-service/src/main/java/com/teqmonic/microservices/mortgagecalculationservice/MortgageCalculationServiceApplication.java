package com.teqmonic.microservices.mortgagecalculationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MortgageCalculationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MortgageCalculationServiceApplication.class, args);
	}

}
