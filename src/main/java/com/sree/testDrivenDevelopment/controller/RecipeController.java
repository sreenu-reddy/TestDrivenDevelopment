package com.sree.testDrivenDevelopment.controller;

import com.sree.testDrivenDevelopment.Service.RecipeService;
import com.sree.testDrivenDevelopment.command.RecipeCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@Controller
public class RecipeController {

  private final RecipeService recipeService;



    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;

    }

    @GetMapping("/recipe/{recipeId}/show")
    public String getRecipe(@PathVariable String recipeId, Model model){

        model.addAttribute("recipe",recipeService.findById(Long.valueOf(recipeId)));

     return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeForm";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors()
                    .forEach(objectError -> log.error(objectError.toString()));
            return "recipe/recipeForm";
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/"+ savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{updateRecipeId}/update")
    public String updateRecipe(@PathVariable String updateRecipeId,Model model){

        log.debug("updated recipe iD: "+updateRecipeId);

        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(updateRecipeId)));
        return "recipe/updateRecipe";

    }

    @GetMapping("/recipe/{deleteId}/delete")
    public String deleteRecipe(@PathVariable String deleteId){
       log.debug("Deleted id :"+deleteId);
       recipeService.deleteById(Long.valueOf(deleteId));
       return "redirect:/";
    }



}
