package com.example.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.currencyconversionservice.CurrencyExchangeProxy;
import com.example.currencyconversionservice.bean.CurrencyConversionBean;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	CurrencyExchangeProxy proxy;
	
	private Logger logger =LoggerFactory.getLogger(this.getClass());

	@GetMapping(path="/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		Map uriVariables=new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		logger.info("In CurrencyConversionController");
		ResponseEntity<CurrencyConversionBean> respEntity= new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);
		CurrencyConversionBean bean=respEntity.getBody();
		return new CurrencyConversionBean(bean.getId(), from, to, bean.getConversionMultiple(), quantity, quantity.multiply(bean.getConversionMultiple()), bean.getPort());
		
		//return new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);
	}
	
	@GetMapping(path="/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		Map uriVariables=new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		logger.info("In CurrencyConversionController");
		/*ResponseEntity<CurrencyConversionBean> respEntity= new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);
		CurrencyConversionBean bean=respEntity.getBody();
		return new CurrencyConversionBean(bean.getId(), from, to, bean.getConversionMultiple(), quantity, quantity.multiply(bean.getConversionMultiple()), bean.getPort());*/
		
		CurrencyConversionBean bean=proxy.getValue(from, to);
		
		return new CurrencyConversionBean(bean.getId(), from, to, bean.getConversionMultiple(), quantity, quantity.multiply(bean.getConversionMultiple()), bean.getPort());
	}
}
