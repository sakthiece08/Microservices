package com.teqmonic.microservices.mortgagerateservice.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MortgageRates {

	@JsonProperty("amortization_period")
	private int amortization;

	@JsonProperty("mortgage_rate")
	private double mortgageRate;
	
	@JsonProperty("profile_rating")
	private String profileRating;

	@JsonProperty("mortgage_type")
	private String mortgageType;

}
