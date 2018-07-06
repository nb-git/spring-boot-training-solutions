package de.innogy.emobility.springtraining.beershop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class for RabbitMQ
 */
@Configuration
@EnableScheduling
public class RabbitConfig {

    private ObjectMapper objectMapper;

    @Autowired
    public RabbitConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Create a Bean for RabbitTemplate to be able to autowire it.
     * Just needed to set the messageConverter or other properties
     *
     * @param connectionFactory ConnectionFactory
     *
     * @return {@link RabbitTemplate}
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(amqpJsonConverter());
        return rabbitTemplate;
    }

    /**
     * Jackson MessageConverter to send Objects as Json
     *
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public Jackson2JsonMessageConverter amqpJsonConverter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
