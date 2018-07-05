package de.innogy.emobility.springtraining.service;

import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerShopService {
    @Autowired
    private BeerRepository beerRepository;
    
    public void addToStock(List<BeerItem> beerItems) {
        beerRepository.saveAll(beerItems);
    }
    
    public BeerItem extractBeerItem(BeerItem beerItem) {
        BeerItem beerItem1 = sumUp(beerRepository.findByBeerName(beerItem.getBeerName()));
        beerRepository.deleteByBeerName(beerItem.getBeerName());
        return beerRepository.save(beerItem1);
        
    }
    public boolean hasEnoughOf(BeerItem beerItem){
        final BeerItem sumOfItems = sumUp(beerRepository.findByBeerName(beerItem.getBeerName()));
        if (sumOfItems ==null) {
            return false;
        }
        return sumOfItems.getQuantity()>beerItem.getQuantity();
    }
    
    private boolean isBlank(List<BeerItem> beerItemList) {
        return beerItemList == null || beerItemList.isEmpty();
    }
    
    private BeerItem sumUp(List<BeerItem> beerItemList) {
        if (isBlank(beerItemList)) {
            return null;
        }
        BeerItem beerItem = new BeerItem(beerItemList.get(0).getBeerName(), 0);
        beerItemList.forEach(item -> beerItem.addQuantity(item.getQuantity()));
        return beerItem;
    }
}
