package de.innogy.emobility.springtraining.beersupplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//Lesson 4.3 Enable Discovery Client
@EnableDiscoveryClient
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BeerSupplierApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerSupplierApplication.class, args);
	}
}
