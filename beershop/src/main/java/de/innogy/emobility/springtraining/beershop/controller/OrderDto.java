package de.innogy.emobility.springtraining.beershop.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    @NotNull
    private String client;

    @Min(1)
    private int quantity;

    @NotNull
    private String beerName;
}
