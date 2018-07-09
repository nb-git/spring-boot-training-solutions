package de.innogy.emobility.springtraining.beershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrder {

    private String client;
    private String beerName;
    private Integer quantity;
}