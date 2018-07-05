package de.innogy.emobility.springtraining.controller;

import de.innogy.emobility.springtraining.model.Beer;
import de.innogy.emobility.springtraining.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/supply")
public class SupplyController {

    private BeerService beerService;

    @Autowired
    public SupplyController(BeerService beerService) {
        this.beerService = beerService;
    }

    @PostMapping(value = "/add")
    public void addBeer(@Valid @RequestBody Beer beer) {
        beerService.addToStock(beer);
    }

    @PutMapping(value = "/update")
    public void updateBeer(@Valid @RequestBody Beer beer) {
        beerService.updateBeer(beer);
    }
    @DeleteMapping(value = "/remove/{name}")
    public void deleteBeer(@PathVariable String name) {
        // TODO: Add Exception Handling
        beerService.removeBeer(name);
    }

    @GetMapping("/all")
    public List<Beer> getAll() {
        return beerService.provideStock();
    }

}
