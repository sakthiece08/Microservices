/**
 * 
 */
package com.teqmonic.microservices.mortgagerateservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.teqmonic.microservices.mortgagerateservice.bean.MortgageRateRequest;
import com.teqmonic.microservices.mortgagerateservice.bean.MortgageRates;
import com.teqmonic.microservices.mortgagerateservice.bean.MortgageRatesResponseData;
import com.teqmonic.microservices.mortgagerateservice.enitity.MortgageProfileEntity;
import com.teqmonic.microservices.mortgagerateservice.errorhandler.ResourceNotFoundException;
import com.teqmonic.microservices.mortgagerateservice.errorhandler.model.ResponseCodes;
import com.teqmonic.microservices.mortgagerateservice.jpa.repository.MortgageProfileRespository;
import com.teqmonic.microservices.mortgagerateservice.service.util.ProfileRatingEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@RefreshScope
@Service
@Slf4j
public class MortgageRateService {
	
	@Autowired
	private MortgageProfileRespository mortgageProfileRespository;
	
	@Value("${mortgage-rate.min}")
	private String minMortgageRate;
	
	@Autowired
	private Environment environment;

	/**
	 * @param request 
	 * @return
	 */
	public MortgageRatesResponseData getAllMortgageRates() {
		log.info("Start of getMortgageRate");

		List<MortgageRates> mortgageRatesList = new ArrayList<>();
		List<MortgageProfileEntity> mortgageData = mortgageProfileRespository.findAll();
		
		if (!ObjectUtils.isEmpty(mortgageData)) {
		    log.info("MortgageProfileEntity List: {}", mortgageData.toString());
			mortgageRatesList = mortgageData.stream()
					.filter(entity -> entity.getMortgageRateEntity().getMortgageRate() > Double.parseDouble(minMortgageRate))
					.map(entity -> MortgageRates.builder().amortization(entity.getAmortizationPeriod())
							.mortgageType(entity.getMortgageType()).profileRating(entity.getProfileRating())
							.mortgageRate(entity.getMortgageRateEntity().getMortgageRate()).build())
					.toList();
		}
		MortgageRatesResponseData mortgageRatesResponseData = MortgageRatesResponseData.builder()
				.mortgageRates(mortgageRatesList).environment(environment.getProperty("local.server.port")).build();

		/*
		 * MortgageRatesResponseData mortgageRatesResponseData = new
		 * MortgageRatesResponseData(); mortgageRatesResponseData =
		 * convertJsonToJava(MORTAGE_RATE_SUCCESS_RESPONSE_FILE,
		 * MortgageRatesResponseData.class);
		 */
		log.info("End of getMortgageRate");
		return mortgageRatesResponseData;
	}
	
	
	/**
	 * Retrieves Mortgage rate response by using Profile code
	 * 
	 * @param profileCode
	 * @return
	 */
	public MortgageRatesResponseData getMortgageRateByProfileCode(String profileCode) {
		log.info("Start of getMortgageRateByProfileCode");
		
		List<MortgageRates> mortgageRatesList = new ArrayList<>();
		List<MortgageProfileEntity> mortgageData = mortgageProfileRespository.findByProfileRating(profileCode);
		
		if (ObjectUtils.isEmpty(mortgageData)) {
			throw new ResourceNotFoundException(ResponseCodes.RESOURCE_NOT_FOUND, "No mortgage ratest available for the Profile code: " + profileCode);
		}

		log.info("MortgageProfileEntity List: {}", mortgageData.toString());
		mortgageRatesList = mortgageData.stream().filter(
				entity -> entity.getMortgageRateEntity().getMortgageRate() > Double.parseDouble(minMortgageRate))
				.map(entity -> MortgageRates.builder().amortization(entity.getAmortizationPeriod())
						.mortgageType(entity.getMortgageType()).profileRating(entity.getProfileRating())
						.mortgageRate(entity.getMortgageRateEntity().getMortgageRate()).build())
				.toList();
		
		MortgageRatesResponseData mortgageRatesResponseData = MortgageRatesResponseData.builder()
				.mortgageRates(mortgageRatesList).environment(environment.getProperty("local.server.port")).build();
		
		log.info("End of getMortgageRateByProfileCode");
		return mortgageRatesResponseData;
	}

}
