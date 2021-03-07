package com.sree.testDrivenDevelopment.controller;

import com.sree.testDrivenDevelopment.Service.RecipeService;
import com.sree.testDrivenDevelopment.command.RecipeCommand;
import com.sree.testDrivenDevelopment.domain.Recipe;
import com.sree.testDrivenDevelopment.exceptions.NotFoundException;
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
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;


   RecipeController controller;


    @Captor
    ArgumentCaptor<Long> argumentCaptor;

    @Captor
    ArgumentCaptor<RecipeCommand> recipeCommandArgumentCaptor;



    @Mock
    Model model;
    @Mock
    BindingResult bindingResult;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getRecipe(){
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        given(recipeService.findById(argumentCaptor.capture())).willReturn(recipe);

//        When
        String page = controller.getRecipe(recipe.getId().toString(),model);
//        Then
//

      then(recipeService).should().findById(argumentCaptor.getValue());
      assertEquals(page,"recipe/show");
      then(model).should().addAttribute(eq("recipe"),any(Recipe.class));
      then(recipeService).shouldHaveNoMoreInteractions();
    }


    @Test
    void testGetRecipeNotFound() throws Exception{
        given(recipeService.findById(anyLong())).willThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404Error"));
        then(recipeService).should().findById(anyLong());
        then(recipeService).shouldHaveNoMoreInteractions();
    }

    @Test
    void testNumberFormatWhenGettingRecipeById() throws Exception{
//        given(recipeService.findById(any())).willThrow(NumberFormatException.class);

        mockMvc.perform(get("/recipe/asdf/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400Error"));
    }


    @Test
    void testStatusOfGetRecipePage() throws Exception{
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        given(recipeService.findById(anyLong())).willReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
              .andExpect(status().isOk())
              .andExpect(model().attributeExists("recipe"))
              .andExpect(view().name("recipe/show"));
    }


    @Test
    void testNewRecipeFrom(){
//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

//        When
        String page = controller.newRecipe(model);
//        Then
        assertEquals("recipe/recipeForm",page);
        then(model).should().addAttribute(eq("recipe"),recipeCommandArgumentCaptor.capture());
        then(model).shouldHaveNoMoreInteractions();

    }



    @Test
    void testNewRecipeFormStatus() throws Exception{

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"));
    }

    @Test
    void testSavedRecipePost() {
//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);
        given(recipeService.saveRecipeCommand(any(RecipeCommand.class))).willReturn(command);
//        when
        String viewName = controller.saveOrUpdate(command,bindingResult);
//        Then

        assertEquals("redirect:/recipe/2/show",viewName);
        then(recipeService).should().saveRecipeCommand(recipeCommandArgumentCaptor.capture());
        then(recipeService).shouldHaveNoMoreInteractions();

    }

    @Test
    void testSavedRecipePostStatus() throws Exception{
//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);
        given(recipeService.saveRecipeCommand(any(RecipeCommand.class))).willReturn(command);

//        then
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("description","something")
                 .param("direction","some direction"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    void testSavedRecipePostFailValidation() throws Exception{
        //        Given
//        RecipeCommand command = new RecipeCommand();
//        command.setId(2L);
//        given(recipeService.saveRecipeCommand(any(RecipeCommand.class))).willReturn(command);

//        Then
        mockMvc.perform(post("/recipe")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id",""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeForm"));
    }


    @Test
    void testGetUpdateView()  {
//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);
        given(recipeService.findCommandById(argumentCaptor.capture())).willReturn(command);

//        when
       var viewName= controller.updateRecipe(command.getId().toString(),model);

//       then
        assertEquals("recipe/updateRecipe",viewName);
        then(model).should().addAttribute(eq("recipe"),recipeCommandArgumentCaptor.capture());
        then(recipeService).should(times(1)).findCommandById(argumentCaptor.getValue());
        then(recipeService).shouldHaveNoMoreInteractions();
    }


    @Test
    void testStatusOfUpdate() throws Exception{
//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);
        given(recipeService.findCommandById(argumentCaptor.capture())).willReturn(command);

//        Then
        mockMvc.perform(get("/recipe/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/updateRecipe"))
                .andExpect(model().attributeExists("recipe"));
        then(recipeService).should(times(1)).findCommandById(argumentCaptor.getValue());
        then(recipeService).shouldHaveNoMoreInteractions();
    }



    @Test
    void testDeleteRecipe() {
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

//       When
        String  page = controller.deleteRecipe(recipe.getId().toString());

//       Then
        assertEquals("redirect:/",page);
        then(recipeService).should().deleteById(argumentCaptor.capture());
        assertEquals(recipe.getId(),argumentCaptor.getValue());
        then(recipeService).shouldHaveNoMoreInteractions();

    }

    @Test
    void testDeleteRecipeByIdStatus() throws Exception{
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

//        Then
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        then(recipeService).should().deleteById(argumentCaptor.capture());
        assertEquals(recipe.getId(),argumentCaptor.getValue());
        then(recipeService).shouldHaveNoMoreInteractions();
    }
}