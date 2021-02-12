package com.sree.testDrivenDevelopment.Service;

import com.sree.testDrivenDevelopment.command.RecipeCommand;
import com.sree.testDrivenDevelopment.converter.RecipeCommandToRecipe;
import com.sree.testDrivenDevelopment.converter.RecipeToRecipeCommand;
import com.sree.testDrivenDevelopment.domain.Recipe;
import com.sree.testDrivenDevelopment.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import java.util.Set;
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand toRecipeCommand;
    private final RecipeCommandToRecipe toRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand toRecipeCommand, RecipeCommandToRecipe toRecipe) {
        this.recipeRepository = recipeRepository;
        this.toRecipeCommand = toRecipeCommand;
        this.toRecipe = toRecipe;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("i am in the service impl");
        Set<Recipe> recipes = new HashSet<>();
         recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
         return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        log.debug("In Recipe By id");
      var returnedRecipe= recipeRepository.findById(id);
      if (returnedRecipe.isPresent()){
          return returnedRecipe.get();
      }else{
          throw new RuntimeException("Recipe Not Found");
      }
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
//        todo unittest
         Recipe recipe= toRecipe.convert(recipeCommand);
         assert recipe!=null;
        var savedRecipe =  recipeRepository.save(recipe);
        log.debug("Saved recipe iD: "+ savedRecipe.getId());
        return toRecipeCommand.convert(savedRecipe);
    }

    @Transactional
    @Override
    public RecipeCommand findCommandById(Long valueOf) {
        var returnedRecipe  =   recipeRepository.findById(valueOf);
        return returnedRecipe.map(toRecipeCommand::convert).orElse(null);
    }

    @Override
    public void deleteById(Long deleteId) {
        log.debug("Deleted Id: "+ deleteId);
        recipeRepository.deleteById(deleteId);
    }
}
