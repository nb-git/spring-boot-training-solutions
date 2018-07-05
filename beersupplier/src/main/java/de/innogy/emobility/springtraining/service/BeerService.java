package de.innogy.emobility.springtraining.service;

import de.innogy.emobility.springtraining.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BeerService {

    private Map<String, Beer> availableBeer;

    @PostConstruct
    public void init() {
        availableBeer = new HashMap<>();
        availableBeer.put("Snake Venom",
                          Beer.builder().alcoholVol(67.5).name("Snake Venom").bottleSizeInMl(500).build());
        availableBeer.put("Sink the Bismarck!",
                          Beer.builder().alcoholVol(41.0).name("Sink the Bismarck!").bottleSizeInMl(375).build());
        availableBeer
                .put("Innogy Pils", Beer.builder().alcoholVol(5.0).name("Innogy Pils").bottleSizeInMl(500).build());
        availableBeer.put("Innogy Bock",
                          Beer.builder().alcoholVol(8.5).name("Innogy Bock").bottleSizeInMl(5000).build());
        availableBeer.put("Faxe", Beer.builder().alcoholVol(5.0).name("Faxe").bottleSizeInMl(1000).build());
        availableBeer.put("Elephant Beer",
                          Beer.builder().alcoholVol(11.0).name("Elephant Beer").bottleSizeInMl(500).build());
        availableBeer.put("Innogy Radler",
                          Beer.builder().alcoholVol(2.5).name("Innogy Radler").bottleSizeInMl(500).build());
        availableBeer.put("Innogy Alkoholfrei",
                          Beer.builder().alcoholVol(0.0).name("Innogy Alkoholfrei").bottleSizeInMl(500).build());
    }

    public Beer provideBeerByName(String name) {
        // TODO add Exception if beer is not available
        return availableBeer.get(name);
    }

    public List<Beer> provideStock() {
        return new ArrayList<>(availableBeer.values());
    }

    public void addToStock(Beer beer) {
        availableBeer.put(beer.getName(), beer);
        log.info(beer.getName() + " was added to stock.");
    }

    public void removeBeer(String beerName) {
        // TODO: Add Exception Handling
        availableBeer.remove(beerName);
        log.info(beerName + " was removed from stock.");
    }

    public void updateBeer(Beer beer){
        // TODO: Add Exception Handling
        Beer oldBeer = provideBeerByName(beer.getName());
        availableBeer.put(beer.getName(), beer);
        log.info(beer.getName() + " was updated.");

    }

}
