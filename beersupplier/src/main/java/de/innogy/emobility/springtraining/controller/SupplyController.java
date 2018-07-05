package de.innogy.emobility.springtraining.controller;

import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/supply")
public class SupplyController {
    private BeerRepository beerRepository;
    
    @Autowired
    public SupplyController(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }
    
    @PostMapping(value = "/addBeer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BeerItem placeOrder(@RequestBody BeerItem beerItem) {
        return beerRepository.add(beerItem);
    }
    
    @GetMapping("/getAll")
    public Collection<BeerItem> getAll() {
        return beerRepository.findAll();
    }
    
    @GetMapping("/getByName")
    public BeerItem getBeerByName(@RequestParam String name) {
        return beerRepository.findByBeerName(name);
    }
}
