package de.innogy.emobility.springtraining.beersupplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO: Lesson 4.1 Enable Discovery Client
//TODO: Lesson 4.2 Enable Circuit Breaker
@SpringBootApplication
public class BeerSupplierApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerSupplierApplication.class, args);
	}
}
