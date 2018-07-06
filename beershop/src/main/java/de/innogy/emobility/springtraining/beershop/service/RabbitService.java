package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.controller.DeliveryDTO;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.repository.BeerItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitService {

    private BeerItemRepository beerItemRepository;

    @Autowired
    public RabbitService(BeerItemRepository beerItemRepository) {
        this.beerItemRepository = beerItemRepository;
    }

    @RabbitListener(queues = "#{directQueue.name}")
    private void receiveDelivery(DeliveryDTO deliveryDTO){
        BeerItem beerItem = deliveryDTO.getBeer();
        beerItem.setStock(beerItem.getStock() + deliveryDTO.getQuantity());
        beerItemRepository.save(beerItem);
    }

    @RabbitListener(queues = "#{directQueue.name}")
    private void removeBeer(BeerItem beerItem){
        beerItemRepository.delete(beerItem);
        log.info(beerItem.getName() + " was removed from database");
    }
}
