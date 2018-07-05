package de.innogy.emobility.springtraining.service;

import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.model.BeerOrder;
import de.innogy.emobility.springtraining.repository.BeerRepository;
import de.innogy.emobility.springtraining.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class BeerService {
    private BeerRepository beerRepository;
    private OrderRepository orderRepository;
    private Map<String, List<BeerItem>> beerStock;
    
    @Autowired
    public BeerService(BeerRepository beerRepository, OrderRepository orderRepository) {
        this.beerRepository = beerRepository;
        this.orderRepository = orderRepository;
    }
    
    public BeerItem extractBeer(String client, String beerName) {
        if (beerStock.containsKey(client)) {
            List<BeerItem> beerItemList = beerStock.get(client);
            final Optional<BeerItem> beerItem = beerItemList.stream().filter(item -> beerName.equals(item.getBeerName())).findFirst();
            beerItemList.remove(beerItem.orElse(null));
            return beerItem.orElse(null);
        }
        return null;
    }
    
    @PostConstruct
    public void init() {
        this.beerStock = new HashMap<>();
    }
    
    public String placeOrder(BeerOrder beerOrder) {
        if (isBlank(beerOrder)) return null;
        final BeerItem beerItem = beerRepository.findByBeerName(beerOrder.getBeerName());
        if (beerItem == null) {
            return "No such beer available!";
        }
        if (beerItem.reduceIfAvailable(beerOrder.getQuantity())) {
            prepareBeerItemsFor(beerOrder);
            BeerOrder beerOrderSaved = orderRepository.save(beerOrder);
            return "Order #" + beerOrderSaved.getId() + " has been accepted.";
        }
        return "Not enough beer of this brand!";
    }
    
    private boolean isBlank(BeerOrder beerOrder) {
        return beerOrder == null || beerOrder.isEmpty();
    }
    
    private void prepareBeerItemsFor(BeerOrder beerOrder) {
        String client = beerOrder.getClient();
        if (!beerStock.containsKey(client)) {
            beerStock.put(client, new ArrayList<>());
        }
        beerStock.get(client).add(new BeerItem(beerOrder.getBeerName(), beerOrder.getQuantity()));
    }
    
    public List<BeerOrder> getOrdersForClient(String client) {
        if (client == null) return null;
        return orderRepository.findBeerOrderByClient(client);
    }
    
    public BeerOrder findBeerOrderById(Integer index) {
        if (index == null) return null;
        return orderRepository.findBeerOrderById(index);
    }
    
    public List<BeerItem> extractAllBeerItemsReadyFor(String client) {
        return beerStock.remove(client);
    }
}
