package com.teqmonic.microservices.mortgagecalculationservice.bean;

import java.math.BigDecimal;
import java.util.List;

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
public class MortgageRatesResponseData {
	
	@JsonProperty("mortgage_rates")
	private List<MortgageRates> mortgageRates;
	
	@JsonProperty("environment")
	private String environment;

}
