/**
 * 
 */
package com.teqmonic.microservices.mortgagecalculationservice.service;


import static com.teqmonic.microservices.mortgagecalculationservice.service.util.CommonUtil.convertJsonToJava;
import static com.teqmonic.microservices.mortgagecalculationservice.service.util.Constants.MORTAGE_RATE_SUCCESS_RESPONSE_FILE;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageDetailsResponse;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRates;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRatesResponseData;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRequest;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageResponse;
import com.teqmonic.microservices.mortgagecalculationservice.configurations.EndPointConfig;
import com.teqmonic.microservices.mortgagecalculationservice.errorhandler.ResourceNotFoundException;
import com.teqmonic.microservices.mortgagecalculationservice.errorhandler.model.ResponseCodes;
import com.teqmonic.microservices.mortgagecalculationservice.openfeign.iMortgageRateProxy;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class MortgageCalculationService {

	Logger logger = LoggerFactory.getLogger(MortgageCalculationService.class);
	
	// @RequiredArgsConstructor will initialize only below private access modifiers objects
	
	private final RestTemplate restTemplate;
	
	private final EndPointConfig endPointConfig;
	
	private final iMortgageRateProxy mortgageRateProxy;
	
	private final ExchangeRateService exchangeRateService;
	

	/**
	 * GetMortgageDetails with Circuit breaker
	 * @return
	 */
	@CircuitBreaker(name="MortgageDetails", fallbackMethod = "getMortgageDetailsFromCache")
	public MortgageResponse getMortgageDetails(boolean isMockResponse, boolean isFeignProxy, MortgageRequest mortgageRequest) {
		logger.info("Start of getMortgageDetails {}, isMockResponse {}, isFeignProxy {} ", mortgageRequest, isMockResponse, isFeignProxy);
		
		MortgageRatesResponseData mortgageRatesResponseData = new MortgageRatesResponseData();
		mortgageRatesResponseData = orchestrateEndpointCall(isMockResponse, isFeignProxy, mortgageRequest);
			
		if (ObjectUtils.isEmpty(mortgageRatesResponseData.getMortgageRates())) {
			throw new ResourceNotFoundException(ResponseCodes.RESOURCE_NOT_FOUND, "No mortgage details availble for the given request criteria");
		}
		 
		MortgageResponse mortgageResponse = populateResponse(mortgageRequest, mortgageRatesResponseData);
		logger.info("End of getMortgageDetails {} ", mortgageResponse);
		return mortgageResponse;
	}
	
	public MortgageResponse getMortgageDetailsGroupByProfileCode(boolean isMockResponse, boolean isFeignProxy, MortgageRequest mortgageRequest) {
		logger.info("Start of getMortgageDetails {} ", mortgageRequest);
		
		MortgageRatesResponseData mortgageRatesResponseData = new MortgageRatesResponseData();
		mortgageRatesResponseData = orchestrateEndpointCall(isMockResponse, isFeignProxy, mortgageRequest);
			
		if (ObjectUtils.isEmpty(mortgageRatesResponseData.getMortgageRates())) {
			throw new ResourceNotFoundException(ResponseCodes.RESOURCE_NOT_FOUND, "No mortgage details availble for the given request criteria");
		}
		 
		MortgageResponse mortgageResponse = populateResponseGroupByProfileCode(mortgageRequest, mortgageRatesResponseData);
		logger.info("End of getMortgageDetails {} ", mortgageResponse);
		return mortgageResponse;
	}
	
	/**
     * Fallback method for 4xx exceptions, just percolate the exception back.
     */
	public MortgageResponse getMortgageDetailsFromCache(HttpClientErrorException ex) {
		logger.info("Rest client error from getMortgageDetails ");
		throw ex;
	}
	
	/**
     * Fallback method for 4xx exceptions in case of Feign, just percolate the exception back.
     */
	public MortgageResponse getMortgageDetailsFromCache(FeignClientException ex) {
		logger.info("Feign error in getMortgageDetailsFromCache ");
		throw ex;
	}
	
	/**
     * Fallback method for all other exception types.
     */
	public MortgageResponse getMortgageDetailsFromCache(boolean isMockResponse, boolean isFeignProxy, MortgageRequest mortgageRequest, Exception ex) {
		logger.info("Start of getMortgageDetailsFromCache {} ", ex);
		
		MortgageRatesResponseData mortgageRatesResponseData = convertJsonToJava(MORTAGE_RATE_SUCCESS_RESPONSE_FILE, MortgageRatesResponseData.class);
		MortgageResponse mortgageResponse = populateResponse(mortgageRequest, mortgageRatesResponseData);
		logger.info("End of getMortgageDetailsFromCache {} ");
		return mortgageResponse;
	}

	/**
	 * Call either stub, Feign client or Rest template
	 * 
	 * @param isMockResponse
	 * @param isFeignProxy
	 * @param mortgageRequest
	 * @return
	 */
	private MortgageRatesResponseData orchestrateEndpointCall(boolean isMockResponse, boolean isFeignProxy, MortgageRequest mortgageRequest) {
		MortgageRatesResponseData mortgageRatesResponseData;
		if (isMockResponse) {
			mortgageRatesResponseData = convertJsonToJava(MORTAGE_RATE_SUCCESS_RESPONSE_FILE, MortgageRatesResponseData.class);
		}
		
		else if (isFeignProxy) {
			logger.info("mortgageRateProxy {} ", mortgageRateProxy);
			ResponseEntity<MortgageRatesResponseData> responseEntity = mortgageRateProxy.getMortgageDetailsFeign(mortgageRequest.getProfileRating());
			mortgageRatesResponseData = responseEntity.getBody();
		}
		else {
			logger.info("mortgageRateUrl {} ", (endPointConfig.getEndpoints().getMortgageRateByProfile()));
			ResponseEntity<MortgageRatesResponseData> responseEntity = restTemplate.getForEntity(
					endPointConfig.getEndpoints().getMortgageRateByProfile(), MortgageRatesResponseData.class,
					mortgageRequest.getProfileRating());
			mortgageRatesResponseData = responseEntity.getBody();
		}
		return mortgageRatesResponseData;
	}

	private MortgageResponse populateResponse(MortgageRequest mortgageRequest, MortgageRatesResponseData mortgageRatesResponseData) {
		// populate response data
		MortgageResponse mortgageResponse = new MortgageResponse();
		mortgageResponse.setMortgageAmount(mortgageRequest.getMortgageAmount());

		List<MortgageDetailsResponse> mortgageDetailsList = Optional.ofNullable(mortgageRatesResponseData).stream()
				.map(MortgageRatesResponseData::getMortgageRates)
				.flatMap(List::stream)
				//.map(List::stream).orElseGet(Stream::empty)
				.map(mortgageRates -> MortgageDetailsResponse.builder().amortization(mortgageRates.getAmortization())
						.mortgageRate(mortgageRates.getMortgageRate()).mortgageType(mortgageRates.getMortgageType())
						.paymentFrequency(mortgageRequest.getPaymentFrequency())
						.profileRating(mortgageRates.getProfileRating())
						.mortgagePayment(calculateMortgagePayment(mortgageRates, mortgageRequest.getMortgageAmount()))
						.build())
				.toList();
		
		mortgageResponse.setMortgageDetails(mortgageDetailsList);
		mortgageResponse.setEnvironment(mortgageRatesResponseData.getEnvironment());
		return mortgageResponse;
	}
	
	private MortgageResponse populateResponseGroupByProfileCode(MortgageRequest mortgageRequest, MortgageRatesResponseData mortgageRatesResponseData) {
		// populate response data
		MortgageResponse mortgageResponse = new MortgageResponse();
		mortgageResponse.setMortgageAmount(mortgageRequest.getMortgageAmount());

		Map<String, List<MortgageDetailsResponse>> mortgageDetailsMap = Optional.ofNullable(mortgageRatesResponseData)
		.map(MortgageRatesResponseData::getMortgageRates)
		.map(List::stream).orElseGet(Stream::empty)
		.map(mortgageRates -> MortgageDetailsResponse.builder().amortization(mortgageRates.getAmortization())
				.mortgageRate(mortgageRates.getMortgageRate()).mortgageType(mortgageRates.getMortgageType())
				.paymentFrequency(mortgageRequest.getPaymentFrequency())
				.profileRating(mortgageRates.getProfileRating())
				.mortgagePayment(calculateMortgagePayment(mortgageRates, mortgageRequest.getMortgageAmount()))
				.build())
		.collect(Collectors.groupingBy(MortgageDetailsResponse::getProfileRating)); // group by ProfileRating
		
		mortgageResponse.setMortgageDetailsMap(mortgageDetailsMap);
		return mortgageResponse;
	}

	
	/**
	 * @param mortgageRates
	 * @param mortgageAmount
	 * @return
	 */
	private String calculateMortgagePayment(MortgageRates mortgageRates, BigDecimal mortgageAmount) {
		long mortgageValue = (long) (((mortgageRates.getMortgageRate() / 100) / 12) * mortgageAmount.longValue());
		// convert above mortgageValue which is in CAD to USD by getting exchange rate
		double exchageRate = exchangeRateService.getExchangeRate(Optional.empty(), "CAD_USD").exchageRate();
		logger.info("calculateMortgagePayment exchageRate {}" , exchageRate);
		return NumberFormat.getCurrencyInstance(Locale.US).format(mortgageValue * exchageRate);
	}

}
