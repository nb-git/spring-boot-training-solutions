package de.innogy.emobility.springtraining.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerItem {
    private String beerName;
    private Integer quantity;
    
    public BeerItem addQuantity(Integer quantity) {
        setQuantity(getQuantity() + quantity);
        return this;
    }
}
