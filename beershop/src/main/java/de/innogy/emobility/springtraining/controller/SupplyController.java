package de.innogy.emobility.springtraining.controller;

import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.service.BeerShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupplyController {
    @Autowired
    private BeerShopService beerShopService;
    
    @PostMapping
    public String deliverBeer(@RequestBody List<BeerItem> beerItems) {
        beerShopService.addToStock(beerItems);
        return "Thanks for the delivery!";
    }
}
