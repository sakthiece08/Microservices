package com.teqmonic.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRoute(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f->f.addRequestHeader("x-sample-header", "value1"))
						.uri("http://httpbin.org:80"))
				//.route(p -> p.path("/api/v1/mortgage-rates/**")
					//	.uri("lb://mortgage-rate"))
				.route(p -> p.path("/api/v1/mortgage-details/**")
						.uri("lb://mortgage-calculation"))
				.route(p -> p.path("/api/v1/mortgage-details-feign/**")
						.uri("lb://mortgage-calculation"))
				.route(p -> p.path("/mortgage-details-new")
						//.filters(f->f.rewritePath("/api/v1/mortgage-details-new/(?<segment>.*)", "/api/v1/mortgage-details-feign/${segment}"))
						.filters(f->f.rewritePath("/mortgage-details-new", "/api/v1/mortgage-details-feign"))
						.uri("lb://mortgage-calculation"))
				.build();
	}
}
