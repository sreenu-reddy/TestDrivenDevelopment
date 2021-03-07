package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.RecipeCommand;
import com.sree.testDrivenDevelopment.domain.Recipe;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final IngredientToIngredientCommand toIngredientCommand;
    private final NotesToNotesCommand toNotesCommand;

    public RecipeToRecipeCommand(IngredientToIngredientCommand toIngredientCommand,  NotesToNotesCommand toNotesCommand) {
        this.toIngredientCommand = toIngredientCommand;
        this.toNotesCommand = toNotesCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe==null){
            return null;
        }


         final  RecipeCommand recipeCommand = new RecipeCommand();
           recipeCommand.setId(recipe.getId());
           recipeCommand.setCookTime(recipe.getCookTime());
           recipeCommand.setDescription(recipe.getDescription());
           recipeCommand.setDifficulty(recipe.getDifficulty());
           recipeCommand.setPrepTime(recipe.getPrepTime());
           recipeCommand.setDirection(recipe.getDirection());
           recipeCommand.setNotes(toNotesCommand.convert(recipe.getNotes()));
           recipeCommand.setServings(recipe.getServings());
           recipeCommand.setUrl(recipe.getUrl());
           recipeCommand.setSource(recipe.getSource());
         if (recipe.getIngredientSet()!=null&& recipe.getIngredientSet().size()>0){
             recipe.getIngredientSet().forEach(ingredient -> recipeCommand.getIngredientSet().add(toIngredientCommand.convert(ingredient)));
         }
         return recipeCommand; }
}
