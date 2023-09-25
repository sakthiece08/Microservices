/**
 * 
 */
package com.teqmonic.microservices.mortgagecalculationservice.service;


import static com.teqmonic.microservices.mortgagecalculationservice.service.util.CommonUtil.convertJsonToJava;
import static com.teqmonic.microservices.mortgagecalculationservice.service.util.Constants.MORTAGE_RATE_SUCCESS_RESPONSE_FILE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageDetailsResponse;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRates;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRatesResponseData;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRequest;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageResponse;
import com.teqmonic.microservices.mortgagecalculationservice.configurations.RestTemplateConfig;
import com.teqmonic.microservices.mortgagecalculationservice.errorhandler.ResourceNotFoundException;
import com.teqmonic.microservices.mortgagecalculationservice.errorhandler.model.ResponseCodes;

import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class MortgageCalculationService {
	
	Logger logger = LoggerFactory.getLogger(MortgageCalculationService.class);
	
	private RestTemplate restTemplate;
	
	private RestTemplateConfig restTemplateConfig;
	

	/**
	 * @return
	 */
	public MortgageResponse getMortgageDetails(boolean isMockResponse, MortgageRequest mortgageRequest) {
		logger.info("Start of getMortgageDetails {} ", mortgageRequest);
		
		MortgageRatesResponseData mortgageRatesResponseData = new MortgageRatesResponseData();
		if (isMockResponse) {
			mortgageRatesResponseData = convertJsonToJava(MORTAGE_RATE_SUCCESS_RESPONSE_FILE, MortgageRatesResponseData.class);
		}
		else {
			logger.info("mortgageRateUrl {} ", (restTemplateConfig.getEndpoints().getGetMortgageRateByProfile()));
			ResponseEntity<MortgageRatesResponseData> responseEntity = restTemplate.getForEntity(
					restTemplateConfig.getEndpoints().getGetMortgageRateByProfile(), MortgageRatesResponseData.class,
					mortgageRequest.getProfileRating());
			mortgageRatesResponseData = responseEntity.getBody();
		}
			
		if (ObjectUtils.isEmpty(mortgageRatesResponseData.getMortgageRates())) {
			throw new ResourceNotFoundException(ResponseCodes.RESOURCE_NOT_FOUND, "No mortgage details availble for the given request criteria");
		}
		 
		MortgageResponse mortgageResponse = populateResponse(mortgageRequest, mortgageRatesResponseData);
		logger.info("End of getMortgageDetails {} ", mortgageResponse);
		return mortgageResponse;
	}

	private MortgageResponse populateResponse(MortgageRequest mortgageRequest, MortgageRatesResponseData mortgageRatesResponseData) {
		// populate response data
		MortgageResponse mortgageResponse = new MortgageResponse();
		List<MortgageDetailsResponse> mortgageDetailsList = new ArrayList<>();
		mortgageResponse.setMortgageAmount(mortgageRequest.getMortgageAmount());

		mortgageDetailsList = Optional.ofNullable(mortgageRatesResponseData)
				.map(MortgageRatesResponseData::getMortgageRates)
				.map(List::stream).orElseGet(Stream::empty)
				.map(mortgageRates -> MortgageDetailsResponse.builder().amortization(mortgageRates.getAmortization())
						.mortgageRate(mortgageRates.getMortgageRate()).mortgageType(mortgageRates.getMortgageType())
						.paymentFrequency(mortgageRequest.getPaymentFrequency())
						.profileRating(mortgageRates.getProfileRating())
						.mortgagePayment(calculateMortgagePayment(mortgageRates, mortgageRequest.getMortgageAmount()))
						.build())
				.toList();
		
		mortgageResponse.setMortgageDetails(mortgageDetailsList);
		return mortgageResponse;
	}

	/**
	 * @param mortgageRates
	 * @param mortgageAmount
	 * @return
	 */
	private BigDecimal calculateMortgagePayment(MortgageRates mortgageRates, BigDecimal mortgageAmount) {
		long mortgageValue = (long) (((mortgageRates.getMortgageRate() / 100) / 12) * mortgageAmount.longValue());
		return new BigDecimal(mortgageValue);
	}

}
