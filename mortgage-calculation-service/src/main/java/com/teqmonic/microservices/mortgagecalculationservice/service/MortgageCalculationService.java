/**
 * 
 */
package com.teqmonic.microservices.mortgagecalculationservice.service;

import static com.teqmonic.microservices.mortgagecalculationservice.service.util.CommonUtil.convertJsonToJava;
import static com.teqmonic.microservices.mortgagecalculationservice.service.util.Constants.MORTAGE_RATE_SUCCESS_RESPONSE_FILE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageDetailsResponse;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRates;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRatesResponseData;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRequest;
import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageResponse;
import com.teqmonic.microservices.mortgagecalculationservice.controller.MortgageCalculationController;

/**
 * 
 */
@Service
public class MortgageCalculationService {
	
	Logger logger = LoggerFactory.getLogger(MortgageCalculationService.class);

	/**
	 * @return
	 */
	public MortgageResponse getMortgageDetails(MortgageRequest mortgageRequest) {
		logger.info("Start of getMortgageDetails {} ", mortgageRequest);
		
		MortgageRatesResponseData mortgageRatesResponseData = new MortgageRatesResponseData();
		mortgageRatesResponseData = convertJsonToJava(MORTAGE_RATE_SUCCESS_RESPONSE_FILE,
				MortgageRatesResponseData.class);

		// populate response data
		MortgageResponse mortgageResponse = new MortgageResponse();
		List<MortgageDetailsResponse> mortgageDetailsList = new ArrayList<>();
		mortgageResponse.setMortgageAmount(mortgageRequest.getMortgageAmount());

		if (!ObjectUtils.isEmpty(mortgageRatesResponseData.getMortgageRates())) {
			mortgageDetailsList = mortgageRatesResponseData.getMortgageRates().stream()
					.map(mortgageRates -> MortgageDetailsResponse.builder()
							.amortization(mortgageRates.getAmortization())
							.mortgageRate(mortgageRates.getMortgageRate())
							.mortgageType(mortgageRates.getMortgageType())
							.paymentFrequency(mortgageRequest.getPaymentFrequency())
							.profileRating(mortgageRates.getProfileRating())
							.mortgagePayment(
									calculateMortgagePayment(mortgageRates, mortgageRequest.getMortgageAmount()))
							.build())
					.toList();
		}
		mortgageResponse.setMortgageDetails(mortgageDetailsList);
		logger.info("End of getMortgageDetails {} ", mortgageResponse);
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
