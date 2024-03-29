# Microservices


### Ports

|     Application       |     Port          |
| ------------- | ------------- |
| Limits Service | 8080, 8081, ... |
| Spring Cloud Config Server | 8888 |
| Mortgage Calculation Service | 8000, 8001, 8002, ..  |
| Mortgage Rate Service | 8100, 8101, 8102, ... |
| Eureka Naming Server http://localhost:8761 | 8761 |
| Api Gateway | 8765 |
| Zipkin Distributed tracing server | 9411 |

### High Level Architecture
![name-of-you-image](https://github.com/sakthiece08/Microservices/blob/master/mortgage-calculation-service/src/main/resources/img/Mortgage_calc-API.JPG)

### Spring cloud config server
Github repository: https://github.com/sakthiece08/spring-cloud-config

links:
 - http://localhost:8888/mortgage-calculation/dev
 - http://localhost:8888/mortgage-calculation/local
 - http://localhost:8888/mortgage-rate/dev
 - http://localhost:8888/mortgage-rate/local

Please refer to my below blogs related to Microservices fault tolerence and resilience architecture:
https://medium.com/@Teqmonic/microservices-pattern-series-1-fault-tolerance-and-resilience-in-depth-e16c7dd20dcb

https://medium.com/@Teqmonic/microservices-pattern-series-2-circuit-breaker-with-resilience4j-a706d1dddf39

### Integration with Micrometer and Zipkin

```
<dependency>
 <groupId>io.micrometer</groupId>
 <artifactId>micrometer-observation</artifactId>
</dependency>

<!-- OPTION 1: Open Telemetry as Bridge (RECOMMENDED) -->
<!-- Open Telemetry 
   - Simplified Observability (metrics, logs, and traces) -->
<dependency>
 <groupId>io.micrometer</groupId>
 <artifactId>micrometer-tracing-bridge-otel</artifactId>
</dependency>
<dependency>
 <groupId>io.opentelemetry</groupId>
 <artifactId>opentelemetry-exporter-zipkin</artifactId>
</dependency>
```
configurations
```
management.tracing.sampling.probability=0.9
logging.pattern.level=%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]
```
Zipkin image
```
docker run -p 9411:9411 openzipkin/zipkin
>> http://localhost:9411/zipkin/
```
