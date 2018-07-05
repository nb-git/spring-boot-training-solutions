package de.innogy.emobility.springtraining.repository;

import de.innogy.emobility.springtraining.model.BeerItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BeerRepository {
    private List<BeerItem> stock;
    
    public List<BeerItem> saveAll(List<BeerItem> beerItems) {
        stock.addAll(beerItems);
        return null;
    }
    
    public List<BeerItem> findByBeerName(String name) {
        return stock.stream().filter(beer -> name.equals(beer.getBeerName())).collect(Collectors.toList());
    }
    
    public void deleteByBeerName(String beerName) {
        stock.removeAll(findByBeerName(beerName));
    }
    
    public BeerItem save(BeerItem beerItem) {
        stock.add(beerItem);
        return beerItem;
    }
}
