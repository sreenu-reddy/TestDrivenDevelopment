package com.sree.testDrivenDevelopment.Service;

import com.sree.testDrivenDevelopment.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId,Long IngredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
    void deleteByRecipeIdAndIngredientId(Long recipeId,Long ingredientId);
}
