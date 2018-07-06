package de.innogy.emobility.springtraining.beersupplier.service;

import de.innogy.emobility.springtraining.beersupplier.controller.DeliveryDTO;
import de.innogy.emobility.springtraining.beersupplier.controller.OrderDTO;
import de.innogy.emobility.springtraining.beersupplier.exception.NotInStockException;
import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RabbitService {

    @Autowired
    private BeerService beerService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Value("${direct.exchange.routingkey}")
    private String routingKey;

    @RabbitListener(queues = "training.order")
    public void receiveOrder(OrderDTO order) {
        log.info(LocalDateTime.now() + " received order: \"" + order.toString() + "\"");
        try {
            Beer beer = beerService.provideBeerByName(order.getBeerName());
            rabbitTemplate
                    .convertAndSend(directExchange.getName(), routingKey, new DeliveryDTO(order.getQuantity(), beer));
            log.info(order.getQuantity() + " of beer: " + order.getBeerName() + " was delivered");
        } catch (NotInStockException ne) {
            log.error("Beer " + order.getBeerName() + " colud not be delivered as it is not listed", ne);
            rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, new DeliveryDTO(0, null));
        }
    }

}
