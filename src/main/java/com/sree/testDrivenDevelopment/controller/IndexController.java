package com.sree.testDrivenDevelopment.controller;

import com.sree.testDrivenDevelopment.Service.RecipeService;
import com.sree.testDrivenDevelopment.Service.RecipeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/","/index","/index.html"})
    public String getIndexPage(Model model){
       log.debug("getting index page");
        model.addAttribute("recipes",recipeService.getRecipes());

        return "index";
    }
}
