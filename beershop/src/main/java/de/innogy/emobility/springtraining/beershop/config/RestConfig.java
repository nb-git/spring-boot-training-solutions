package de.innogy.emobility.springtraining.beershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    //TODO: Lesson 4.1 make it LoadBalanced to be able to use discovery service names
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
