package com.teqmonic.microservices.mortgagecalculationservice.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MortgageDetailsResponse {

	@JsonProperty("amortization_period")
	private int amortization;
	
	@JsonProperty("mortgage_rate")
	private float mortgageRate;
	
	@JsonProperty("payment_frequency")
	private String paymentFrequency;
	
	@JsonProperty("mortgage_payment")
	private String mortgagePayment;
	
	@JsonProperty("profile_rating")
	private String profileRating;
	
	@JsonProperty("mortgage_type")
	private String mortgageType;

}
