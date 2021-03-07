package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.RecipeCommand;
import com.sree.testDrivenDevelopment.domain.Recipe;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final NotesCommandToNotes toNotes;
    private final IngredientCommandToIngredient toIngredient;

    public RecipeCommandToRecipe(NotesCommandToNotes toNotes, IngredientCommandToIngredient toIngredient) {
        this.toNotes = toNotes;
        this.toIngredient = toIngredient;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {

        if (recipeCommand==null){
            return null;
        }


        final Recipe recipe = new Recipe();
         recipe.setId(recipeCommand.getId());
         recipe.setCookTime(recipeCommand.getCookTime());
         recipe.setPrepTime(recipeCommand.getPrepTime());
         recipe.setDescription(recipeCommand.getDescription());
         recipe.setDifficulty(recipeCommand.getDifficulty());
         recipe.setDirection(recipeCommand.getDirection());
         recipe.setUrl(recipeCommand.getUrl());
         recipe.setServings(recipeCommand.getServings());
         recipe.setSource(recipeCommand.getSource());
         recipe.setNotes(toNotes.convert(recipeCommand.getNotes()));

         if (recipeCommand.getIngredientSet()!=null&&recipeCommand.getIngredientSet().size()>0){
             recipeCommand.getIngredientSet().forEach(
                     ingredientCommand -> recipe.getIngredientSet().add(toIngredient.convert(ingredientCommand))
             );

         }

         return recipe;
    }
}
