package de.innogy.emobility.springtraining.beersupplier.service;

import de.innogy.emobility.springtraining.beersupplier.controller.DeliveryDTO;
import de.innogy.emobility.springtraining.beersupplier.controller.OrderDTO;
import de.innogy.emobility.springtraining.beersupplier.exception.NotInStockException;
import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Autowired
    private FanoutExchange fanoutExchange;

    @Value("${direct.exchange.routingkey}")
    private String routingKey;

    @Value("${order.queue}")
    private String orderQueueName;

    @Scheduled(initialDelay = 5000, fixedRate = 10000)
    public void sendStringToQueue(){
        rabbitTemplate.convertAndSend(orderQueueName, "test");
        log.info("String test was send to " + orderQueueName);
    }

    public void sendDeliveryToDirectExchange(DeliveryDTO delivery){
        rabbitTemplate
                .convertAndSend(directExchange.getName(), routingKey, delivery);
        log.info(delivery.getQuantity() + " of beer: " + delivery.getBeer().getName() + " was delivered");
    }

    public void sendRemovedBeerToFanout(Beer removedBeer) {
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", removedBeer);
        log.info(removedBeer.getName() + " was removed from stock.");
    }

}
