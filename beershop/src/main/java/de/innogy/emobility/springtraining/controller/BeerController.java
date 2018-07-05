package de.innogy.emobility.springtraining.controller;

import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.service.BeerShopService;
import de.innogy.emobility.springtraining.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerController {
    @Autowired
    private BeerShopService beerShopService;
    @Autowired
    private SupplyService supplyService;
    
    @GetMapping("/orderBeer")
    public BeerItem orderBeer(@RequestParam String beerName, @RequestParam Integer quantity) {
        final BeerItem beerItem = new BeerItem(beerName, quantity);
        if (beerShopService.hasEnoughOf(beerItem)) {
            return beerShopService.extractBeerItem(beerItem);
        } else {
            supplyService.fillSupplyWith(beerItem);
            return beerShopService.extractBeerItem(beerItem);
        }
    }
}
