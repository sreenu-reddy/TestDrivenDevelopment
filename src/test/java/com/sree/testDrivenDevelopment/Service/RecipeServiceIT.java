package com.sree.testDrivenDevelopment.Service;

import com.sree.testDrivenDevelopment.command.RecipeCommand;
import com.sree.testDrivenDevelopment.converter.RecipeCommandToRecipe;
import com.sree.testDrivenDevelopment.converter.RecipeToRecipeCommand;
import com.sree.testDrivenDevelopment.domain.Recipe;
import com.sree.testDrivenDevelopment.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecipeServiceIT {

    public static final String DESCRIPTION = "description";


    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeToRecipeCommand toRecipeCommand;
    @Autowired
    RecipeCommandToRecipe toRecipe;

    @Transactional
    @Test
    void saveOfDescription() {
//        Given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = toRecipeCommand.convert(testRecipe);

        //when
        assert testRecipeCommand != null;
        testRecipeCommand.setDescription(DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertNotNull(testRecipeCommand);
        assertEquals(DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategorySet().size(), savedRecipeCommand.getCategorySet().size());
        assertEquals(testRecipe.getIngredientSet().size(), savedRecipeCommand.getIngredientSet().size());

    }
}