package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.IngredientCommand;
import com.sree.testDrivenDevelopment.domain.Ingredient;
import com.sree.testDrivenDevelopment.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure) {
        this.toUnitOfMeasure = toUnitOfMeasure;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand==null){
            return null;
        }



          final Ingredient ingredient = new Ingredient();
            ingredient.setId(ingredientCommand.getId());
        if(ingredientCommand.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(ingredientCommand.getRecipeId());
            recipe.addIngredient(ingredient);
        }
            ingredient.setIngDescription(ingredientCommand.getIngDescription());
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setUnitOfMeasure(toUnitOfMeasure.convert(ingredientCommand.getUnitOfMeasure()));
            return ingredient; }
}
