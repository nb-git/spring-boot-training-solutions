package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.controller.DeliveryDTO;
import de.innogy.emobility.springtraining.beershop.controller.OrderDTO;
import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.exception.SorryDudeAlcoholicOnlyException;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SupplyService {

    private RestTemplate restTemplate;

    @Value("${beer-producer.order.url}")
    private String beerProducerOrderUrl;

    @Value("${beer-producer.supply.all.url}")
    private String beerProducerBeersUrl;

    @Value("${clientName}")
    private String clientName;

    private Map<String, BeerItem> beerItemList;

    @PostConstruct
    public void init() {
        beerItemList = new HashMap<>();
        BeerItem[] beerItems = restTemplate.getForObject(beerProducerBeersUrl, BeerItem[].class);
        for (BeerItem beerItem : Arrays.asList(beerItems)) {
            beerItem.setStock(100);
            beerItemList.put(beerItem.getName(), beerItem);
        }
    }

    @Autowired
    public SupplyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void fillSupplyWith(BeerItem beerItem) {
        DeliveryDTO order = restTemplate
                .postForObject(beerProducerOrderUrl, new OrderDTO(clientName, 1000, beerItem.getName()),
                               DeliveryDTO.class);
        beerItem.setStock(beerItem.getStock() + order.getQuantity());
        beerItemList.put(beerItem.getName(), beerItem);
    }

    public DeliveryDTO orderBeer(OrderDTO orderDTO) throws OutOfBeerException {
        BeerItem beerItem = beerItemList.get(orderDTO.getBeerName());
        if (beerItem.getStock() >= orderDTO.getQuantity()) {
            beerItem.setStock(beerItem.getStock() - orderDTO.getQuantity());
            return new DeliveryDTO(orderDTO.getQuantity(), beerItem);
        } else {
            throw new OutOfBeerException(
                    "Not enough quantity of Beer " + beerItem.getName() + " only " + beerItem.getStock() + " left", beerItem);
        }
    }

    public List<BeerItem> provideMenu() {
        return new ArrayList<>(beerItemList.values());
    }

    public List<BeerItem> provideNonAlcholicBeer() throws SorryDudeAlcoholicOnlyException {
        //TODO: implement this method!
        return null;
    }

    public List<BeerItem> provideBeerDueForResupply() {
        //TODO: implement this method!
        return null;
    }
}
