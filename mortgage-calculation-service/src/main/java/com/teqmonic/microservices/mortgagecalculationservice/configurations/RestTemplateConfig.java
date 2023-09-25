package com.teqmonic.microservices.mortgagecalculationservice.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Validated
@Configuration
@ConfigurationProperties("mortgage-calculation")
public class RestTemplateConfig {
	
	@Valid
	private MortgageRateEndPoints endpoints = new MortgageRateEndPoints(); 
	
	@Getter
	@Setter
	public class MortgageRateEndPoints {
		
		@NotEmpty
		private String getMortgageRateByProfile;
		
	}	

}
