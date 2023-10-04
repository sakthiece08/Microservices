package com.teqmonic.microservices.mortgagecalculationservice.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExchangeRateResponse(@JsonProperty("currency_pair") String currencyPair,
		@JsonProperty("exchange_rate") double exchageRate) {

}
