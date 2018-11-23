package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.controller.DeliveryDto;
import de.innogy.emobility.springtraining.beershop.controller.OrderDto;
import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.repository.BeerItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SupplyService {

    private RestTemplate restTemplate;

    @Value("${beersupplier.order.url}")
    private String beerProducerOrderUrl;

    @Value("${clientName}")
    private String clientName;

    private BeerItemRepository beerItemRepository;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SupplyService(RestTemplate restTemplate, BeerItemRepository beerItemRepository, JdbcTemplate jdbcTemplate) {
        this.restTemplate = restTemplate;
        this.beerItemRepository = beerItemRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void fillSupplyWith(BeerItem beerItem) {
        storeOutgoingOrder(beerItem.getName(), 1000);
        restTemplate.postForObject(beerProducerOrderUrl, new OrderDto(clientName, 1000, beerItem.getName()), DeliveryDto.class);
    }

    public DeliveryDto orderBeer(OrderDto orderDTO) throws OutOfBeerException {
        BeerItem beerItem = beerItemRepository.findById(orderDTO.getBeerName()).orElse(null);
        if (beerItem != null && beerItem.getStock() >= orderDTO.getQuantity()) {
            beerItem.setStock(beerItem.getStock() - orderDTO.getQuantity());
            beerItemRepository.save(beerItem);
            return new DeliveryDto(orderDTO.getQuantity(), beerItem);
        } else {
            throw new OutOfBeerException(
                "Not enough quantity of Beer " + orderDTO.getBeerName() + " only " + (beerItem != null ? beerItem.getStock() : 0) + " left", beerItem);
        }
    }

    public List<BeerItem> provideMenu() {
        return beerItemRepository.findAll();
    }

    private void storeOutgoingOrder(String beerName, int quantity) {
        jdbcTemplate.update("INSERT INTO BEER_STOCK_ORDER (BEER_NAME, QUANTITY) VALUES (?,?)", beerName, quantity);
    }
}
