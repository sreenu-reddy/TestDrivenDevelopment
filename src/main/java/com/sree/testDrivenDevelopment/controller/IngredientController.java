package com.sree.testDrivenDevelopment.controller;

import com.sree.testDrivenDevelopment.Service.IngredientService;
import com.sree.testDrivenDevelopment.Service.RecipeService;
import com.sree.testDrivenDevelopment.Service.UnitOfMeasureService;
import com.sree.testDrivenDevelopment.command.IngredientCommand;
import com.sree.testDrivenDevelopment.command.RecipeCommand;
import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService  recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService ofMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService ofMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.ofMeasureService = ofMeasureService;
    }

    @GetMapping("/recipe/{recipeID}/ingredients")
    public String listIngredients(@PathVariable String recipeID, Model model){
        log.debug("ingredients of Recipe :"+recipeID);
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeID)));
        return "recipe/ingredient/list";

    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientID}/show")
    public String showIngredient(@PathVariable String recipeId,@PathVariable String ingredientID,Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientID)));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId,@PathVariable String ingredientId, Model model){

        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));

        model.addAttribute("uomList",ofMeasureService.listAllUom());
        return "recipe/ingredient/ingredientForm";

    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved recipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId,Model model){

           RecipeCommand recipeCommand =recipeService.findCommandById(Long.valueOf(recipeId));

           IngredientCommand ingredientCommand = new IngredientCommand();
           ingredientCommand.setRecipeId(recipeCommand.getId());
           model.addAttribute("ingredient",ingredientCommand);

           ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
           model.addAttribute("uomList",ofMeasureService.listAllUom());
        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/ingredient/new")
    public String newIngredient(Model model){
        IngredientCommand ingredientCommand = new IngredientCommand();
        model.addAttribute("ingredient",new IngredientCommand());
        model.addAttribute("uomList",ofMeasureService.listAllUom());
        return "recipe/ingredient/ingredientForm";
    }


    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,@PathVariable String ingredientId){

        ingredientService.deleteByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId));

        return "redirect:/recipe/"+recipeId+"/ingredients";

    }
}
