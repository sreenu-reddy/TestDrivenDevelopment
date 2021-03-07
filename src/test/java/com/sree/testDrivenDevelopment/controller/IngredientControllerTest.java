package com.sree.testDrivenDevelopment.controller;

import com.sree.testDrivenDevelopment.Service.IngredientService;
import com.sree.testDrivenDevelopment.Service.RecipeService;
import com.sree.testDrivenDevelopment.Service.UnitOfMeasureService;
import com.sree.testDrivenDevelopment.command.IngredientCommand;
import com.sree.testDrivenDevelopment.command.RecipeCommand;
import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.Ingredient;
import com.sree.testDrivenDevelopment.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService ofMeasureService;

    @Mock
    Model model;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<RecipeCommand> recipeCommandArgumentCaptor;

    @Captor
    ArgumentCaptor<IngredientCommand> ingredientCommandArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> argumentCaptor;

    @Captor
    ArgumentCaptor<Recipe> recipeArgumentCaptor;

    IngredientController controller;

    @BeforeEach
    void setUp() {
        controller = new IngredientController(recipeService, ingredientService, ofMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void listIngredients()  {
//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        given(recipeService.findCommandById(anyLong())).willReturn(command);
//        When
       String page = controller.listIngredients(command.getId().toString(),model);
//        then
        assertEquals("recipe/ingredient/list",page);
        then(model).should().addAttribute(eq("recipe"),recipeCommandArgumentCaptor.capture());
        assertEquals(1,recipeCommandArgumentCaptor.getValue().getId());
        then(recipeService).should().findCommandById(argumentCaptor.capture());
        assertEquals(1,argumentCaptor.getValue());
        then(recipeService).shouldHaveNoMoreInteractions();
    }

    @Test
    void testListIngredientsStatus() throws Exception{
//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        given(recipeService.findCommandById(anyLong())).willReturn(command);

//        Then
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"));
        then(recipeService).should().findCommandById(anyLong());
        then(recipeService).shouldHaveNoMoreInteractions();


    }


    @Test
    void testShowIngredient() {
        IngredientCommand command = new IngredientCommand();
        command.setId(2L);
        given(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).willReturn(command);
//        When
        String page = controller.showIngredient("1","1",model);

//       Then
        assertEquals("recipe/ingredient/show",page);
        then(model).should().addAttribute(eq("ingredient"),ingredientCommandArgumentCaptor.capture());
        assertEquals(2L,ingredientCommandArgumentCaptor.getValue().getId());
        then(model).shouldHaveNoMoreInteractions();
        then(ingredientService).should().findByRecipeIdAndIngredientId(argumentCaptor.capture(),anyLong());
        assertEquals(1L,argumentCaptor.getValue());
        then(ingredientService).shouldHaveNoMoreInteractions();
    }

    @Test
    void testShowIngredientStatus() throws Exception{
//        Given
        given(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).willReturn(new IngredientCommand());

//        Then
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(status().isOk());

    }




    @Test
    void testUpdateIngredient() {
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        recipe.addIngredient(ingredient);
        IngredientCommand command = new IngredientCommand();
        command.setId(1L);
        given(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).willReturn(command);

        Set<UnitOfMeasureCommand> measureCommand = new HashSet<>();
        given(ofMeasureService.listAllUom()).willReturn(measureCommand);

//        When
        String  page = controller.updateIngredient(recipe.getId().toString(),ingredient.getId().toString(),model);

//        Then
        assertEquals("recipe/ingredient/ingredientForm",page);
        then(ingredientService).should().findByRecipeIdAndIngredientId(argumentCaptor.capture(),anyLong());
        assertEquals(1L,argumentCaptor.getValue());
        then(ingredientService).shouldHaveNoMoreInteractions();
        then(ofMeasureService).should().listAllUom();
        then(ofMeasureService).shouldHaveNoMoreInteractions();
        then(model).should().addAttribute(eq("ingredient"),ingredientCommandArgumentCaptor.capture());
        assertEquals(1L,ingredientCommandArgumentCaptor.getValue().getId());
        then(model).should().addAttribute(eq("uomList"),anySet());
        then(model).shouldHaveNoMoreInteractions();

    }

    @Test
    void testUpdateIngredientStatus() throws Exception{
//        Given
        given(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).willReturn(new IngredientCommand());
        given(ofMeasureService.listAllUom()).willReturn(new HashSet<>());

//        Then
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("recipe/ingredient/ingredientForm"));
    }

    @Test
    void testSaveOrUpdateIngredient() {
//        Given
        IngredientCommand command = new IngredientCommand();
        command.setId(1L);
        command.setRecipeId(2L);
        given(ingredientService.saveIngredientCommand(any())).willReturn(command);

//        When
        String page = controller.saveOrUpdateIngredient(command);

//        Then
        assertEquals("redirect:/recipe/2/ingredient/1/show",page);
        then(ingredientService).should().saveIngredientCommand(ingredientCommandArgumentCaptor.capture());
        assertEquals(1,ingredientCommandArgumentCaptor.getValue().getId());
        then(ingredientService).shouldHaveNoMoreInteractions();


    }

    @Test
    void testSaveOrUpdateIngredientStatus()throws Exception{
//        Given
        IngredientCommand command = new IngredientCommand();
        command.setId(1L);
        command.setRecipeId(2L);
        given(ingredientService.saveIngredientCommand(any())).willReturn(command);

//        Then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("ingDescription","some"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/1/show"));

    }

    @Test
    void testNewIngredient(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        IngredientCommand  ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        given(recipeService.findCommandById(anyLong())).willReturn(recipeCommand);

        Set<UnitOfMeasureCommand> measureCommand = new HashSet<>();
        given(ofMeasureService.listAllUom()).willReturn(measureCommand);

//        When
        String page = controller.newIngredient(recipe.getId().toString(),model);

//        Then
        assertEquals("recipe/ingredient/ingredientForm",page);
        then(model).should().addAttribute(eq("ingredient"),ingredientCommandArgumentCaptor.capture());
        then(model).should().addAttribute(eq("uomList"),anySet());
        then(ofMeasureService).should().listAllUom();
        then(ofMeasureService).shouldHaveNoMoreInteractions();
        then(recipeService).should().findCommandById(argumentCaptor.capture());
        assertEquals(1,argumentCaptor.getValue());
        then(recipeService).shouldHaveNoMoreInteractions();
    }


    @Test
    void testNewIngredientStatus() throws Exception{
//        Given
        given(recipeService.findCommandById(anyLong())).willReturn(new RecipeCommand());
        given(ofMeasureService.listAllUom()).willReturn(new HashSet<>());

//        Then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient","uomList"))
                .andExpect(view().name("recipe/ingredient/ingredientForm"));
    }


    @Test
    void deleteIngredientOfParticularRecipeId() throws Exception{
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        recipe.addIngredient(ingredient);
//        When
        String page= controller.deleteIngredient(recipe.getId().toString(),ingredient.getId().toString());

//        Then
        assertEquals("redirect:/recipe/1/ingredients",page);
        mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));

    }
}