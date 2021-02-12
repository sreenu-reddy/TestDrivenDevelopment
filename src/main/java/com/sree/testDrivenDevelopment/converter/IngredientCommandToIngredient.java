package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.IngredientCommand;
import com.sree.testDrivenDevelopment.domain.Ingredient;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @Synchronized
    @Override
    public Ingredient convert( @NonNull IngredientCommand ingredientCommand) {
          final Ingredient ingredient = new Ingredient();
            ingredient.setId(ingredientCommand.getId());
            ingredient.setIngDescription(ingredientCommand.getIngDescription());
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setUnitOfMeasure(unitOfMeasure.convert(ingredientCommand.getUnitOfMeasure()));
            return ingredient; }
}
