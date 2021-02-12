package com.sree.testDrivenDevelopment.controller;


import com.sree.testDrivenDevelopment.Service.RecipeService;
import com.sree.testDrivenDevelopment.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    IndexController controller;

    @Mock
    Model model;

    @Captor
    ArgumentCaptor<Set<Recipe>> argumentCaptor;

    @BeforeEach
    void setUp() {
        controller =new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {
//        Given
        Recipe recipe = new Recipe();
        Set<Recipe> recipeSet = new HashSet<>();
        recipe.setId(1L);
        recipeSet.add(recipe);
        given(recipeService.getRecipes()).willReturn(recipeSet);

//        When
        var page= controller.getIndexPage(model);

//        Then
        then(recipeService).should().getRecipes();
        then(recipeService).shouldHaveNoMoreInteractions();
        then(model).should().addAttribute(eq("recipes"),argumentCaptor.capture());
        assertEquals(1,argumentCaptor.getValue().size());
        assertEquals("index",page);
    }


    @Test
    void testStatusOfIndexPage() throws Exception{
//        Given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

//        Then
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(model().attributeExists("recipes"))
                .andExpect(view().name("index"));

    }

}