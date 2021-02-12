package com.sree.testDrivenDevelopment.controller;

import com.sree.testDrivenDevelopment.Service.RecipeService;
import com.sree.testDrivenDevelopment.command.RecipeCommand;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
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

    @Mock
    Model model;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getRecipe() throws Exception{
//        Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        given(recipeService.findById(argumentCaptor.capture())).willReturn(recipe);

//        When

//        Then
      mockMvc.perform(get("/recipe/1/show"))
              .andExpect(status().isOk())
              .andExpect(model().attributeExists("recipe"))
              .andExpect(view().name("recipe/show"));

      then(recipeService).should().findById(argumentCaptor.getValue());
      then(recipeService).shouldHaveNoMoreInteractions();
    }

    @Test
    void testNewRecipeForm() throws Exception{

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeForm"));
    }

    @Test
    void testSavedRecipePost() throws Exception{
//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);
        given(recipeService.saveRecipeCommand(any(RecipeCommand.class))).willReturn(command);
//        when
        String viewName = controller.saveOrUpdate(command);
//        Then
        mockMvc.perform(post("/recipe")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id","")
        .param("description","something"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
        assertEquals("redirect:/recipe/2/show",viewName);

    }
    @Test
    void testGetUpdateView() throws Exception {
//        Given
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);
        given(recipeService.findCommandById(argumentCaptor.capture())).willReturn(command);

//        when
       var viewName= controller.updateRecipe(command.getId().toString(),model);

        mockMvc.perform(get("/recipe/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().attributeExists("recipe"));

        assertEquals("recipe/recipeForm",viewName);
        then(recipeService).should(times(2)).findCommandById(argumentCaptor.getValue());
        then(recipeService).shouldHaveNoMoreInteractions();
    }

    @Test
    void testDeleteRecipe() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);

//        then
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        then(recipeService).should().deleteById(anyLong());
        then(recipeService).shouldHaveNoMoreInteractions();

    }
}