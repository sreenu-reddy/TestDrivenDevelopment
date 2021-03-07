package com.sree.testDrivenDevelopment.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private Long id;
    private Long recipeId;
    @NotEmpty
    @Size(min = 3,max = 255,message = "Description is need")
    private String ingDescription;

    @Positive
    private BigDecimal amount;
    @NotBlank
    private UnitOfMeasureCommand unitOfMeasure;
}
