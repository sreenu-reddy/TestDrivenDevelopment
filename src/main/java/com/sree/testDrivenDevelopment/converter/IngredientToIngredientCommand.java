package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.IngredientCommand;
import com.sree.testDrivenDevelopment.domain.Ingredient;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand) {
        this.unitOfMeasureCommand = unitOfMeasureCommand;
    }

    @Synchronized
    @Override
    public IngredientCommand convert( @NonNull Ingredient ingredient) {


           final IngredientCommand ingredientCommand = new IngredientCommand();
           ingredientCommand.setId(ingredient.getId());
           ingredientCommand.setIngDescription(ingredient.getIngDescription());
           ingredientCommand.setAmount(ingredient.getAmount());
           ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand.convert(ingredient.getUnitOfMeasure()));
           return ingredientCommand; }
}
