package de.innogy.emobility.springtraining.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {

    private int quantity;

    private Beer beer;

}
