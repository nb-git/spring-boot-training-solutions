package de.innogy.emobility.springtraining.repository;

import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.model.BeerOrder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    private List<BeerOrder> beerOrders;
    
    @PostConstruct
    public void init() {
        beerOrders = new ArrayList<>();
    }
    
    public BeerOrder save(BeerOrder beerOrder) {
        beerOrders.add(beerOrder);
        beerOrder.setId(beerOrders.indexOf(beerOrder));
        return beerOrder;
    }
    
    public BeerOrder findBeerOrderById(Integer index) {
        return beerOrders.get(index);
    }
    
    public List<BeerOrder> findAll() {
        return beerOrders;
    }
    
    public List<BeerOrder> findBeerOrderByClient(String client) {
        return beerOrders.stream().filter(beerOrder -> client.equals(beerOrder.getClient())).collect(Collectors.toList());
    }
}
