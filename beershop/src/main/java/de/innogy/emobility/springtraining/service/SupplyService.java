package de.innogy.emobility.springtraining.service;

import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.model.BeerOrder;
import de.innogy.emobility.springtraining.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SupplyService {
    private final RestTemplate restTemplate;
    @Value("${beer-producer.url}")
    String beerProducerUrl;
    @Value("${clientName}")
    String clientName;
    private BeerRepository beerRepository;
    
    public SupplyService(RestTemplateBuilder restTemplateBuilder,BeerRepository beerRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.beerRepository=beerRepository;
    }
    
    public void fillSupplyWith(BeerItem beerItem) {
        String response = restTemplate.postForObject(beerProducerUrl+"/placeOrder", new BeerOrder(clientName, beerItem.getBeerName(), beerItem.getQuantity(), "I will never pay"), String.class);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String,String> requestParam=new HashMap<>();
        requestParam.put("client", clientName);
        requestParam.put("beerName", beerItem.getBeerName());
        final BeerItem beerItemResponse = restTemplate.getForObject(beerProducerUrl + "/takeBeer", BeerItem.class, requestParam);
        beerRepository.save(beerItemResponse);
    }
}
