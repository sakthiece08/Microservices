package com.teqmonic.microservices.mortgagecalculationservice.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageLenderResponse;

@RefreshScope
@Service
public class MortgageLendersService {

	Logger logger = LoggerFactory.getLogger(MortgageLendersService.class);

	@Value("${mortgage-calculation.lenders.list}")
	private List<String> mortgageLendersList;

	public MortgageLenderResponse getLenders() {
		logger.info("Start of getLenders ");
		List<String> filteredMortgageList = Optional.ofNullable(mortgageLendersList).stream()
				.map(i -> i.toString().toUpperCase()).toList();
		return MortgageLenderResponse.builder().lendersList(filteredMortgageList).build();
	}

}
