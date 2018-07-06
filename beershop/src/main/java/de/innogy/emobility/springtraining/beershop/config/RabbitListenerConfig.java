package de.innogy.emobility.springtraining.beershop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitListenerConfig implements RabbitListenerConfigurer {

    private ObjectMapper objectMapper;

    @Value("${fanout.exchange}")
    private String fanoutExchangeName;

    @Value("${fanout.exchange.queue}")
    private String fanoutExchangeQueueName;

    @Value("${direct.exchange}")
    private String directExchangeName;

    @Value("${direct.exchange.queue}")
    private String directExchangeQueueName;

    @Value("${direct.exchange.routingkey}")
    private String directExchangeRoutingKey;

    @Autowired
    public RabbitListenerConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    /**
     * Setup FanoutExchange.
     *
     * @return {@link FanoutExchange}
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchangeName);
    }

    /**
     * Setup Queue for FanoutExchange.
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue fanoutQueue() {
        return new Queue(fanoutExchangeQueueName);
    }

    /**
     * Bind fanoutExchange-queue to fanoutExchange-exchange
     *
     * @return {@link Binding} of fanoutExchange-queue
     */
    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

    // Direct-Exchange Settings

    /**
     * Setup DirectExchange.
     *
     * @return {@link DirectExchange}
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchangeName);
    }

    /**
     * Setup Queue for DirectExchange.
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue directQueue() {
        return new Queue(directExchangeQueueName);
    }

    /**
     * Bind direct-queue to direct-exchange
     *
     * @return {@link Binding} of direct-queue
     */
    @Bean
    Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(directExchangeRoutingKey);
    }

    /**
     * Setup MessageConverter for JSON-Payload deserialization.
     *
     * @return {@link MappingJackson2MessageConverter}
     */
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }

    /**
     * Setup DefaultMessageHandlerMethodFactory to use Jackson as MessageConverter.
     *
     * @return {@link DefaultMessageHandlerMethodFactory}
     */
    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    /**
     * Setup RabbitListeners to use custom MessageHandlerFactory.
     *
     * @param rabbitListenerEndpointRegistrar RabbitListenerEndpointRegistrar
     */
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
