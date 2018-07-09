package de.innogy.emobility.springtraining.beersupplier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO: make this class your Entity!
public class Beer {

    @NotNull
    private String name;

    @NotNull
    private Integer bottleSizeInMl;

    @NotNull
    private Double alcoholVol;

}
