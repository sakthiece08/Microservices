package com.teqmonic.microservices.mortgagecalculationservice.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.teqmonic.microservices.mortgagecalculationservice.bean.ExchangeRateResponse;
import com.teqmonic.microservices.mortgagecalculationservice.configurations.EndPointConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
	
	Logger logger = LoggerFactory.getLogger(MortgageCalculationService.class);
	
    private final EndPointConfig endPointConfig;
	
	private final WebClient webClient;
	
	@Value("${x-api-Key.exchangeRate}")
	private String apiKey;
	
	/**
	 * Retrieves exchange rate for the given currency pair (E.g: USD_CAD)
	 * 
	 * @param currencyPair
	 * @param apiKeyHeader
	 * @return
	 */
	public ExchangeRateResponse getExchangeRate(Optional<String> apiKeyHeader, String currencyPair) {
		logger.info("Start of getExchangeRate, apiKeyHeader {}, currencyPair {}" , apiKeyHeader, currencyPair);
		
		var headers = new HttpHeaders();
		headers.add("X-Api-Key", apiKeyHeader.orElse(apiKey));
		
		logger.info("ExchangeRateUrl {} ", (endPointConfig.getEndpoints().getExchangeRate()));
		// use web client	
		return this.webClient.get()
				.uri(endPointConfig.getEndpoints().getExchangeRate() + "?pair={currencyPair}", currencyPair)
				.headers(h -> h.addAll(headers)).retrieve().bodyToMono(ExchangeRateResponse.class).block();		
		
		/*
		   String urlTemplate = UriComponentsBuilder.fromHttpUrl(restTemplateConfig.getEndpoints().getExchangeRate())
				.queryParam("pair", currencyPair).toUriString();
		 * ResponseEntity<ExchangeRateResponse> responseEntity =  restTemplate.exchange(urlTemplate, HttpMethod.GET, httpEntity,
		 * ExchangeRateResponse.class); return responseEntity.getBody();
		 */
	}

}
