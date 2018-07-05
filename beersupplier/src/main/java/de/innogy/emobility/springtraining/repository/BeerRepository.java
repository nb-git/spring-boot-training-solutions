package de.innogy.emobility.springtraining.repository;

import de.innogy.emobility.springtraining.model.BeerItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BeerRepository {
    private Map<String, BeerItem> beerItems;
    
    public BeerItem findByBeerName(String beerName) {
        return beerItems.get(beerName);
    }
    
    public BeerRepository(@Value("${beers}") String[] beerNames, @Value("${quantity}") String quantity) {
        Integer initialQuantities = Integer.valueOf(quantity);
        beerItems = new HashMap<>();
        Arrays.asList(beerNames).forEach(name -> beerItems.put(name, new BeerItem(name, initialQuantities)));
    }
    
    public BeerItem add(BeerItem beerItem) {
        BeerItem addedBeerItem;
        if (beerItems.containsKey(beerItem.getBeerName())) {
            addedBeerItem = beerItems.get(beerItem.getBeerName()).add(beerItem.getQuantity());
        } else {
            beerItems.put(beerItem.getBeerName(), beerItem);
            addedBeerItem = beerItems.get(beerItem.getBeerName());
        }
        return addedBeerItem;
    }
    
    public Collection<BeerItem> findAll() {
        return beerItems.values();
    }
}
