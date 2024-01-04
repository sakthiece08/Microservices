#### Note: This is in continuation to the 'Docker' branch, please refer to the Docker branch readme file before coming here for better understanding



## Images
https://hub.docker.com/
- sakthiece08/mortgage-rate-service:0.0.11-SNAPSHOT
- sakthiece08/mortgage-calculation-service:0.0.11-SNAPSHOT


### Maven build image
```
mvn spring-boot:build-image -DskipTest
```
### Configure environment variable for Feign proxy (Service Discovery)
Service name = mortgage-rate

Environment variable "MORTGAGE_RATE_SERVICE_HOST" is autmatically available in Kubernetes. Localhost is used only for local deployments.
Whenever we launch a new POD, corresponding environment variable would be created "SERVICE_NAME_SERVICE_HOST"
```
@FeignClient(name = "mortgage-rate", url = "${MORTGAGE_RATE_SERVICE_HOST:http://localhost}:8100")
public interface iMortgageRateProxy {
	@GetMapping("/api/v1/mortgage-rates/{profileRating}")
	public ResponseEntity<MortgageRatesResponseData> getMortgageDetailsFeign(@PathVariable String profileRating);
}
```
### Kubernetes Deployment
```
Kubectl create deployment mortgage-rate --image=sakthiece08/mortgage-rate-service:0.0.11-SNAPSHOT
Kubectl expose deployment mortgage-rate --type=LoadBalancer --port=8100

Kubectl create deployment mortgage-calculation --image=sakthiece08/mortgage-calculation-service:0.0.12-SNAPSHOT
Kubectl expose deployment mortgage-calculation --type=LoadBalancer --port=8000


watch -n 0.9 "curl 'http://34.122.149.185:8100/api/v1/mortgage-rates/A’ "
curl -X GET 'http://34.28.34.161:8000/api/v1/mortgage-details-feign' -H "Content-Type: application/json" -d '{"mortgage_amount": 100000, "profile_rating": "A", "payment_frequency": "monthly"}'

```

### Generate declarative configurations (YAML)
```
Kubectl get deployment mortgage-rate -o yaml >> deployment.yaml
Kubectl get service mortgage-rate -o yaml >> service.yaml

Kubectl diff -f deployment.yaml
Kubectl apply -f deployment.yaml
```
After updating replicas as 2, we can see the requests are hitting both the Pods through Load Balancer.

### Configure environment variables
```
template:
    spec:
      containers:
      - image: sakthiece08/mortgage-calculation-service:0.0.12-SNAPSHOT
        env:
          - name: MORTGAGE_RATE_URI
            value: http://mortgage-rate
```

### Configmap
Instead of hardcoding above environment variables, Kubernetes provides Configmap to store the external environment variables
```
apiVersion: v1
data:
  MORTGAGE_RATE_URI: http://mortgage-rate
kind: ConfigMap
metadata:
  name: mortgage-calculation
  namespace: default

```
