#### Note: This is in continuation to the 'Docker' branch, please refer to the Docker branch readme file before coming here for better understanding



## Images
https://hub.docker.com/
- sakthiece08/mortgage-rate-service:0.0.11-SNAPSHOT
- sakthiece08/mortgage-calculation-service:0.0.11-SNAPSHOT


### Maven build image
```
mvn spring-boot:build-image -DskipTest
```
### Configure environment variable for Feign proxy

Environment variable "MORTGAGE_RATE_SERVICE_HOST" is autmatically available in Kubernetes. Localhost is used only for local deployments.
```
@FeignClient(name = "mortgage-rate", url = "${MORTGAGE_RATE_SERVICE_HOST:http://localhost}:8100")
public interface iMortgageRateProxy {
	@GetMapping("/api/v1/mortgage-rates/{profileRating}")
	public ResponseEntity<MortgageRatesResponseData> getMortgageDetailsFeign(@PathVariable String profileRating);
}
```
