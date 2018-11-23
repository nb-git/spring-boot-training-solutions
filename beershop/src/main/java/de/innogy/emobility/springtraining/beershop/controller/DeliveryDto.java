package de.innogy.emobility.springtraining.beershop.controller;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    private Integer quantity;

    private BeerItem beer;

}
