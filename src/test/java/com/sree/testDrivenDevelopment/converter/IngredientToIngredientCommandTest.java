package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.IngredientCommand;
import com.sree.testDrivenDevelopment.domain.Ingredient;
import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Long UOM_ID = 2L;

    IngredientToIngredientCommand toIngredientCommand;

    @BeforeEach
    void setUp() {
        toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void testEmptyObject(){
        Ingredient ingredient = new Ingredient();
        ingredient.setUnitOfMeasure(new UnitOfMeasure());
        assertNotNull(ingredient);
    }


    @Test
    void convert() {
//        Given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setIngDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        ingredient.setUnitOfMeasure(unitOfMeasure);
//        When
        IngredientCommand command = toIngredientCommand.convert(ingredient);

//        Then
        assertNotNull(command);
        assertNotNull(command.getUnitOfMeasure());
        assertEquals(ID_VALUE,command.getId());
        assertEquals(DESCRIPTION,command.getIngDescription());
        assertEquals(AMOUNT,command.getAmount());
        assertEquals(UOM_ID,command.getUnitOfMeasure().getId());

    }
}