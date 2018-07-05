package de.innogy.emobility.springtraining.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BeerOrder {
    String client, beerName;
    Integer quantity;
    String paymentInfo;
    Integer id;
    
    public boolean isEmpty() {
        return getBeerName() == null || getClient() == null || getQuantity() == null || getQuantity() == 0 || getPaymentInfo() == null;
    }
}
