package de.innogy.emobility.springtraining.beershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//TODO: Lesson 4.3 Enable Discovery Client
//TODO: Lesson 4.4 Enable Circuit Breaker
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BeerShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerShopApplication.class, args);
	}
}
