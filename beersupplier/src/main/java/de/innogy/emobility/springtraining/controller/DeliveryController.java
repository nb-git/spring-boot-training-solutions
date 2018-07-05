package de.innogy.emobility.springtraining.controller;

import de.innogy.emobility.springtraining.model.BeerItem;
import de.innogy.emobility.springtraining.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/deliver")
public class DeliveryController {
    private BeerService beerService;
    private RestTemplate restTemplate;
    @Value("${beer-shop.url}")
    private String clientUrl;
    
    @Autowired
    public DeliveryController(BeerService beerService, RestTemplateBuilder builder) {
        this.beerService = beerService;
        this.restTemplate = builder.build();
    }
    
    @GetMapping("/allOrdersForClient")
    public String deliverAll(@RequestParam String client) {
        if (client == null) return null;
        final List<BeerItem> ordersForClient = beerService.extractAllBeerItemsReadyFor(client);
        return "Delivered all beerItems to the client " + client + ". Response: " + deliver(ordersForClient);
    }
    
    private String deliver(List<BeerItem> beerItems) {
        String response = null;
        if (beerItems != null && !beerItems.isEmpty()) {
            response = restTemplate.postForObject(clientUrl, beerItems, String.class);
        }
        return response;
    }
}
