package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.controller.DeliveryDTO;
import de.innogy.emobility.springtraining.beershop.controller.OrderDTO;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.repository.BeerItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RabbitService {

    private BeerItemRepository beerItemRepository;

    @Autowired
    public RabbitService(BeerItemRepository beerItemRepository) {
        this.beerItemRepository = beerItemRepository;
    }

    @RabbitListener(queues = "training.order")
    private void reveiveString(String in){
        log.info("Received String " + in);
    }

    @RabbitListener(queues = "#{directQueue.name}")
    private void receiveDelivery(DeliveryDTO deliveryDTO) {
        if (deliveryDTO.getBeer() != null) {
            Optional<BeerItem> result = beerItemRepository.findById(deliveryDTO.getBeer().getName());
            if (result.isPresent()) {
                BeerItem currentBeer = result.get();
                currentBeer.setStock(currentBeer.getStock() + deliveryDTO.getQuantity());
                beerItemRepository.save(currentBeer);
            }
        }
    }

    @RabbitListener(queues = "#{fanoutQueue.name}")
    private void removeBeer(BeerItem beerItem) {
        beerItemRepository.delete(beerItem);
        log.info(beerItem.getName() + " was removed from database");
    }
}
