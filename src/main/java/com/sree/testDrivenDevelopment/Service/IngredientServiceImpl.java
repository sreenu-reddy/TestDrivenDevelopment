package com.sree.testDrivenDevelopment.Service;

import com.sree.testDrivenDevelopment.command.IngredientCommand;
import com.sree.testDrivenDevelopment.converter.IngredientCommandToIngredient;
import com.sree.testDrivenDevelopment.converter.IngredientToIngredientCommand;
import com.sree.testDrivenDevelopment.domain.Ingredient;
import com.sree.testDrivenDevelopment.domain.Recipe;
import com.sree.testDrivenDevelopment.exceptions.NotFoundException;
import com.sree.testDrivenDevelopment.repository.RecipeRepository;
import com.sree.testDrivenDevelopment.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

   private final IngredientToIngredientCommand toIngredientCommand;
   private final RecipeRepository recipeRepository;
   private final IngredientCommandToIngredient toIngredient;
   private final UnitOfMeasureRepository ofMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand toIngredientCommand, RecipeRepository recipeRepository, IngredientCommandToIngredient toIngredient, UnitOfMeasureRepository ofMeasureRepository) {
        this.toIngredientCommand = toIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.toIngredient = toIngredient;
        this.ofMeasureRepository = ofMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long IngredientId) {
       Optional<Recipe> optionalRecipe =  recipeRepository.findById(recipeId);
        IngredientCommand command = new IngredientCommand();
       if (optionalRecipe.isPresent()){
           Recipe recipe = optionalRecipe.get();
         Optional<IngredientCommand> optionalIngredientCommand=  recipe
                 .getIngredientSet()
                 .stream()
                 .filter(ingredient -> ingredient.getId().equals(IngredientId))
                 .map(toIngredientCommand::convert).findFirst();
         if (optionalIngredientCommand.isPresent()){
             command= optionalIngredientCommand.get();
         }else {
             log.debug("IngredientCommand Is not found");
            throw new NotFoundException("Ingredient ID: "+IngredientId+"Not Found");
         }
       }else{
          log.debug("RecipeCommand Is Not Found");
          throw new NotFoundException("Recipe ID: "+recipeId+"Not Found");
       }
       return command;
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(command.getRecipeId());
        if (optionalRecipe.isEmpty()){
            log.debug("Recipe id is not found: "+command.getRecipeId());
           throw new NotFoundException("Recipe ID: "+command.getRecipeId()+" NOt Found");
        }else{
            Recipe recipe = optionalRecipe.get();

           Optional<Ingredient> optionalIngredient= recipe
                    .getIngredientSet()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();
           if (optionalIngredient.isPresent()){
               Ingredient foundIngredient = optionalIngredient.get();
               foundIngredient.setIngDescription(command.getIngDescription());
               foundIngredient.setAmount(command.getAmount());
              var measure= ofMeasureRepository.findById(command.getUnitOfMeasure().getId());
              if (measure.isPresent()){
                  foundIngredient.setUnitOfMeasure(measure.get());
              }else{
                  throw new NotFoundException("UOM IS NOt FOUND"+command.getUnitOfMeasure().getId());
              }
           }else{
//               add new ingredient
               Ingredient ingredient = toIngredient.convert(command);
               assert ingredient != null;
               recipe.addIngredient(ingredient);
               ingredient.setRecipe(recipe);
           }

//           save recipe
           Recipe savedRecipe= recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredientSet().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if(savedIngredientOptional.isEmpty()){
                savedIngredientOptional = savedRecipe.getIngredientSet().stream()
                        .filter(recipeIngredients -> recipeIngredients.getIngDescription().equals(command.getIngDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return toIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isPresent()){

            log.debug("Recipe found");
            Recipe recipe = optionalRecipe.get();
            Optional<Ingredient> optionalIngredient = recipe.getIngredientSet()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if (optionalIngredient.isPresent()){
                log.debug("Ingredient found");
                log.debug("Deleting Ingredient id:"+recipeId+" of Ingredient "+ingredientId);
                Ingredient ingredient = optionalIngredient.get();
                ingredient.setRecipe(null);
                recipe.getIngredientSet().remove(ingredient);
                recipeRepository.save(recipe);
            }
        }else {
            log.debug("Recipe is not found with ID "+recipeId);
            throw new NotFoundException("Recipe ID: "+recipeId+"Not Found");
        }

    }
}
