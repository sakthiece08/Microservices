package com.example.currencyconversionservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableFeignClients("com.example.currencyconversionservice")
@EnableDiscoveryClient
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context=(ApplicationContext) SpringApplication.run(CurrencyConversionServiceApplication.class, args);
		
		for(String name:context.getBeanDefinitionNames())
		System.out.println(name);
	}
}
