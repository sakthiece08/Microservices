package com.teqmonic.microservices.mortgagecalculationservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.teqmonic.microservices.mortgagecalculationservice.bean.MortgageRatesResponseData;

//@FeignClient(name = "mortgage-rate", url = "http://localhost:8100") harded to a single instance when no naming server
// @FeignClient(name = "mortgage-rate") // Load balancing multiple instances of Downstream services through naming server

//@FeignClient(name = "mortgage-rate", url = "${MORTGAGE_RATE_SERVICE_HOST:http://localhost}:8100")
@FeignClient(name = "mortgage-rate", url = "${MORTGAGE_RATE_URI:http://localhost}:8100")
public interface iMortgageRateProxy {

	@GetMapping("/api/v1/mortgage-rates/{profileRating}")
	public ResponseEntity<MortgageRatesResponseData> getMortgageDetailsFeign(@PathVariable String profileRating);

}
