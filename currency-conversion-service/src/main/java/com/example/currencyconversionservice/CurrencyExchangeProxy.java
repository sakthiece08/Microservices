package com.example.currencyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.currencyconversionservice.bean.CurrencyConversionBean;

//@FeignClient(name="currency-exchange-service",url="localhost:8000")
//@FeignClient(name="currency-exchange-service")
@FeignClient(name="netflix-zuul-api-gateway-server")
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeProxy {

	//@GetMapping("/currency-exchange/from/{from}/to/{to}")
	// using zuul http://zull_url:zuul_port/{target_App_name}/{target_uri}
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}") // to connect to Api gateway
	public CurrencyConversionBean getValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
