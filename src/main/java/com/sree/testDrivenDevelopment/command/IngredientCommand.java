package com.sree.testDrivenDevelopment.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private Long id;
    private String ingDescription;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;
}
