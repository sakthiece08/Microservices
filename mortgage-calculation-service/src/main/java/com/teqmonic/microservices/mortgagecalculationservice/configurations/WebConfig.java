package com.teqmonic.microservices.mortgagecalculationservice.configurations;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebConfig {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(Duration.ofMillis(3000)).setReadTimeout(Duration.ofMillis(3000)).build();
	}

	@Bean
	public WebClient webClient() {
		// create reactor netty HTTP client
		@SuppressWarnings("deprecation")
		HttpClient httpClient = HttpClient.create().tcpConfiguration(tcpClient -> {
			tcpClient = tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
			tcpClient = tcpClient.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(3)));
			return tcpClient;
		});
		// create a client http connector using above http client
		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
		// use this configured http connector to build the web client
		return WebClient.builder().clientConnector(connector).build();
	}
}