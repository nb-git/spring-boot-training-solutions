package de.innogy.emobility.springtraining.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrder {
    String client, beerName;
    Integer quantity;
    String paymentInfo;
}