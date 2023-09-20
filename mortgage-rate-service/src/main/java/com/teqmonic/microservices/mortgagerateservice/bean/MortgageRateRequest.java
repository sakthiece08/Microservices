package com.teqmonic.microservices.mortgagerateservice.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
public class MortgageRateRequest {

	@JsonProperty("mortgage_amount")
	private BigDecimal mortgageAmount;
	
	@JsonProperty("payment_frequency")
	private String paymentFrequency;
	
	@JsonProperty("profile_rating")
	private String profileRating;
	
}
