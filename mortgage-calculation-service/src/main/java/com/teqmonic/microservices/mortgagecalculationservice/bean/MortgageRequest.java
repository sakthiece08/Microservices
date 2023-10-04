package com.teqmonic.microservices.mortgagecalculationservice.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@ToString
public class MortgageRequest {

	@JsonProperty("mortgage_amount")
	private BigDecimal mortgageAmount;
	
	@JsonProperty("payment_frequency")
	private String paymentFrequency;
	
	@JsonProperty("profile_rating")
	private String profileRating;
	
}
