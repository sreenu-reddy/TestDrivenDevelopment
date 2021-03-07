package com.sree.testDrivenDevelopment.Service;


import com.sree.testDrivenDevelopment.command.RecipeCommand;
import com.sree.testDrivenDevelopment.converter.RecipeCommandToRecipe;
import com.sree.testDrivenDevelopment.converter.RecipeToRecipeCommand;
import com.sree.testDrivenDevelopment.domain.Recipe;
import com.sree.testDrivenDevelopment.exceptions.NotFoundException;
import com.sree.testDrivenDevelopment.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe toRecipe;
    @Mock
    RecipeToRecipeCommand toRecipeCommand;

    RecipeService recipeService;

    @Captor
    ArgumentCaptor<Long> argumentCaptor;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeServiceImpl(recipeRepository,toRecipeCommand,toRecipe);
    }

    @DisplayName("Testing getRecipes In RecipeServiceImpl")
    @Test
    void getRecipes() {
//        Given
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipeSet.add(recipe);
        given(recipeRepository.findAll()).willReturn(recipeSet);
//      when
       var recipes= recipeService.getRecipes();
//        Then
        assertEquals(1,recipes.size());
        then(recipeRepository).should().findAll();
        then(recipeRepository).shouldHaveNoMoreInteractions();


    }

    @DisplayName("Get recipe By Given Id")
    @Test
    void getRecipeById(){
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);

//        when
         var returnedRecipe= recipeService.findById(1L);

//         then
        assertNotNull(returnedRecipe,"not null recipe should return");
        assertEquals(recipe.getId(),returnedRecipe.getId());
        then(recipeRepository).should().findById(anyLong());
        then(recipeRepository).shouldHaveNoMoreInteractions();
    }
    @DisplayName("Required recipe not found test")
    @Test()
    void testGetRecipeByIdWithNotExist(){
        Optional<Recipe> optionalRecipe = Optional.empty();
        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);
        assertThrows(NotFoundException.class,()->recipeService.findById(1L),"should throw RunTEx");
    }

    @Test
    void testFindCommandById(){
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        given(recipeRepository.findById(argumentCaptor.capture())).willReturn(optionalRecipe);
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        given(toRecipeCommand.convert(any(Recipe.class))).willReturn(command);

//        when
       RecipeCommand command1 =recipeService.findCommandById(1L);

//       then
        assertEquals(1,command1.getId());
        then(recipeRepository).should().findById(argumentCaptor.getValue());
        then(recipeRepository).shouldHaveNoMoreInteractions();
        then(toRecipeCommand).should().convert(any(Recipe.class));
        then(toRecipeCommand).shouldHaveNoMoreInteractions();
    }

    @Test
    void testFindCommandByNotExistId(){
        assertThrows(NotFoundException.class,()->recipeService.findCommandById(1L));
    }


    @Test
    void testSaveRecipeCommand(){
//        Here we can even test all the properties of the recipe but i just matched with id's of
//        the recipe and recipeCommand.

//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        given(toRecipeCommand.convert(any())).willReturn(command);

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        given(toRecipe.convert(any())).willReturn(recipe);
        given(recipeRepository.save(any(Recipe.class))).willReturn(recipe);

//        When
        RecipeCommand command1 = recipeService.saveRecipeCommand(command);

//        then
        assertNotNull(command1,"null Recipe Command returned ");
        assertEquals(recipe.getId(),command1.getId());
        then(recipeRepository).should().save(any(Recipe.class));
        then(recipeRepository).shouldHaveNoMoreInteractions();
        then(toRecipeCommand).should().convert(any(Recipe.class));
        then(toRecipe).should().convert(any(RecipeCommand.class));
        then(toRecipeCommand).shouldHaveNoMoreInteractions();
        then(toRecipe).shouldHaveNoMoreInteractions();
    }

    @Test
    void testSaveRecipeCommandIsNull(){
        assertThrows(NullPointerException.class,()->recipeService.saveRecipeCommand(null));
    }


    @Test
    void testDeleteByID(){
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
//         when
        recipeService.deleteById(1L);

//        then
        then(recipeRepository).should().deleteById(anyLong());
        then(recipeRepository).shouldHaveNoMoreInteractions();

    }
}