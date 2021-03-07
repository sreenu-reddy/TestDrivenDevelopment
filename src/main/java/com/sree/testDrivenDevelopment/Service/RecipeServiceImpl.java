package com.sree.testDrivenDevelopment.Service;

import com.sree.testDrivenDevelopment.command.RecipeCommand;
import com.sree.testDrivenDevelopment.converter.RecipeCommandToRecipe;
import com.sree.testDrivenDevelopment.converter.RecipeToRecipeCommand;
import com.sree.testDrivenDevelopment.domain.Recipe;
import com.sree.testDrivenDevelopment.exceptions.NotFoundException;
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
        log.debug("getting recipes from repository");
        Set<Recipe> recipes = new HashSet<>();
         recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
         return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        log.debug(" Getting Recipe of: "+id);
      var returnedRecipe= recipeRepository.findById(id);
      if (returnedRecipe.isPresent()){
          return returnedRecipe.get();
      }else{
          throw new NotFoundException("Sorry pal!!!! Recipe with ID "+id.toString()+" is Not There");
      }
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
         Recipe recipeFound= toRecipe.convert(recipeCommand);
         if (recipeFound !=null){
             var savedRecipe =  recipeRepository.save(recipeFound);
             log.debug("Saved recipe iD: "+ savedRecipe.getId());
             return toRecipeCommand.convert(savedRecipe);
         }else{
             log.debug("Recipe With ID: "+recipeCommand.getId()+ " Not found");
             throw new NullPointerException("required recipe Not Found");
         }

    }

    @Transactional
    @Override
    public RecipeCommand findCommandById(Long valueOf) {
        var returnedRecipe  =   recipeRepository.findById(valueOf);

        if (returnedRecipe.isPresent()){
            log.debug("Found recipe ID: "+valueOf);
            Recipe recipe = returnedRecipe.get();
            return toRecipeCommand.convert(recipe);
        }else {
            log.debug("Recipe With ID: "+valueOf+ " Not found");
            throw new NotFoundException("Recipe with ID "+valueOf.toString()+" Not Found");
        }

    }

    @Override
    public void deleteById(Long deleteId) {
        log.debug("Deleted Id: "+ deleteId);
        recipeRepository.deleteById(deleteId);
    }
}
