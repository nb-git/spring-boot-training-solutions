package de.innogy.emobility.springtraining.controller;

import de.innogy.emobility.springtraining.model.Beer;
import de.innogy.emobility.springtraining.model.DeliveryDTO;
import de.innogy.emobility.springtraining.model.OrderDTO;
import de.innogy.emobility.springtraining.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/order")
public class OrderController {
    @Autowired
    private BeerService beerService;
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryDTO placeOrder(@Valid @RequestBody OrderDTO order) {
        String beerName = order.getBeerName();
        // TODO add ExceptionHandling
        Beer beer = beerService.provideBeerByName(beerName);
        return new DeliveryDTO(order.getQuantity(), beer);
    }

}
