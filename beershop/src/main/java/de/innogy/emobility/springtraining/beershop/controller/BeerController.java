package de.innogy.emobility.springtraining.beershop.controller;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class BeerController {

    private SupplyService supplyService;

    /*
     * TODO 4.3: On a Get-Request, request all available Beers from supplier,
     * if the supplier is not reachable provide a list of surrogate beers.
     */
    @Value("${beersupplier.supply.all.url}")
    private String beerProducerBeersUrl;

    private RestTemplate restTemplate;

    @Autowired
    public BeerController(SupplyService supplyService, RestTemplate restTemplate) {
        this.supplyService = supplyService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/order")
    public DeliveryDto orderBeer(@Valid @RequestBody OrderDto orderDTO) throws OutOfBeerException {
        return supplyService.orderBeer(orderDTO);
    }

    @GetMapping("/menu")
    public List<BeerItem> getMenu() {
        return supplyService.provideMenu();
    }

    @HystrixCommand(fallbackMethod = "provideFallback")
    @GetMapping("/fallback")
    public List<BeerItem> getFallbackMenu() {
        BeerItem[] beerItems = restTemplate.getForObject(beerProducerBeersUrl, BeerItem[].class);
        return beerItems != null ? Arrays.asList(beerItems) : null;
    }

    private List<BeerItem> provideFallback(){
        BeerItem beer1 = new BeerItem("Wasser", 0.0, 1);
        BeerItem beer2 = new BeerItem("Plörrbräu", 8.4, 1);
        return Lists.newArrayList(beer1, beer2);
    }

}
