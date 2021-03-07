package com.sree.testDrivenDevelopment.Service;

import com.sree.testDrivenDevelopment.command.IngredientCommand;
import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.converter.IngredientCommandToIngredient;
import com.sree.testDrivenDevelopment.converter.IngredientToIngredientCommand;
import com.sree.testDrivenDevelopment.converter.UnitOfMeasureCommandToUnitOfMeasure;
import com.sree.testDrivenDevelopment.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.Ingredient;
import com.sree.testDrivenDevelopment.domain.Recipe;
import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import com.sree.testDrivenDevelopment.exceptions.NotFoundException;
import com.sree.testDrivenDevelopment.repository.RecipeRepository;
import com.sree.testDrivenDevelopment.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    private final IngredientToIngredientCommand toIngredientCommand;
    private  final IngredientCommandToIngredient toIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Captor
    ArgumentCaptor<Long> argumentCaptor;

    @Captor
     ArgumentCaptor<Recipe> recipeArgumentCaptor;

    IngredientServiceImpl ingredientService;
    IngredientServiceImplTest() {
        this.toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.toIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }


    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(toIngredientCommand,recipeRepository, toIngredient, unitOfMeasureRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(3L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(2L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3L);
        recipe.addIngredient(ingredient);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);

        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);

//       when
        IngredientCommand ingredientCommand=  ingredientService.findByRecipeIdAndIngredientId(3L,1L);

//      then
        assertEquals(1L,ingredientCommand.getId());
        assertEquals(3L,ingredientCommand.getRecipeId());
        then(recipeRepository).should().findById(argumentCaptor.capture());
        assertEquals(3,argumentCaptor.getValue());
        then(recipeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testFindByRecipeIDNotExistAndIngredientId(){
        Optional<Recipe> optionalRecipe = Optional.empty();
        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);

        assertThrows(NotFoundException.class,()->ingredientService.findByRecipeIdAndIngredientId(1L,3L));

    }

    @Test
    void testFindByRecipeIDAndIngredientIdNotExist(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        given(recipeRepository.findById(anyLong())).willReturn(Optional.of(recipe));

        assertThrows(NotFoundException.class,()->ingredientService.findByRecipeIdAndIngredientId(1L,3L));
    }

    @Test
    void testSaveIngredientCommand(){
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);
        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(2L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        savedRecipe.addIngredient(ingredient);

        given(recipeRepository.findById(anyLong())).willReturn(recipeOptional);
        given(recipeRepository.save(any(Recipe.class))).willReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        then(recipeRepository).should().findById(argumentCaptor.capture());
        assertEquals(2,argumentCaptor.getValue());
        then(recipeRepository).should().save(any(Recipe.class));
        then(recipeRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    void testSaveIngredientWithNoExistRecipe(){
        assertThrows(NotFoundException.class,()->ingredientService.saveIngredientCommand(new IngredientCommand()));
    }

//    @Test
//    void testSaveIngredientWithNoExistUnitOfMeasure(){
//        IngredientCommand command = new IngredientCommand();
//        command.setId(3L);
//        command.setRecipeId(2L);
////        Optional<UnitOfMeasure> ofMeasureOptional = Optional.empty();
//        UnitOfMeasureCommand ofMeasureCommand = new UnitOfMeasureCommand();
//        ofMeasureCommand.setId(null);
//        command.setUnitOfMeasure(ofMeasureCommand);
//        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
//
//        Recipe savedRecipe = new Recipe();
//
//        savedRecipe.setId(2L);
//        Ingredient ingredient = new Ingredient();
//        ingredient.setId(3L);
//        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
//        unitOfMeasure.setId(null);
//        ingredient.setUnitOfMeasure(unitOfMeasure);
//        savedRecipe.addIngredient(ingredient);
//
//        given(recipeRepository.findById(anyLong())).willReturn(recipeOptional);
//        given(recipeRepository.save(any(Recipe.class))).willReturn(savedRecipe);
//
////        given(unitOfMeasureRepository.findById(anyLong())).willReturn(ofMeasureOptional);
//
//       assertThrows(NotFoundException.class,()->ingredientService.saveIngredientCommand(command));
//
//
//    }


    @Test
    void testDeleteIngredient(){
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(3L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(2L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3L);
        recipe.addIngredient(ingredient);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        given(recipeRepository.findById(anyLong())).willReturn(Optional.of(recipe));
        given(recipeRepository.save(any(Recipe.class))).willReturn(recipe);

//        When
        ingredientService.deleteByRecipeIdAndIngredientId(recipe.getId(),ingredient.getId());
//        Then
        then(recipeRepository).should().findById(argumentCaptor.capture());
        assertEquals(3,argumentCaptor.getValue());
        then(recipeRepository).should().save(recipeArgumentCaptor.capture());
        assertEquals(2,recipeArgumentCaptor.getValue().getIngredientSet().size());
        then(recipeRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    void testDeleteIngredientWithNoRecipeExist(){

        assertThrows(NotFoundException.class,()->ingredientService.deleteByRecipeIdAndIngredientId(1L,3L));
    }


}