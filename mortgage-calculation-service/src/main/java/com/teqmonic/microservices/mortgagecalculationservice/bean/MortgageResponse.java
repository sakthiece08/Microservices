/**
 * 
 */
package com.teqmonic.microservices.mortgagecalculationservice.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "mortgageAmount" })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MortgageResponse {
	@JsonProperty("mortgage_amount")
	private BigDecimal mortgageAmount;
	
	@JsonProperty("mortgage_details")
	private List<MortgageDetailsResponse> mortgageDetails;
	
	@JsonProperty("mortgage_details_grouping")
	private Map<String, List<MortgageDetailsResponse>> mortgageDetailsMap;
}
