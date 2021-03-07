package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.IngredientCommand;
import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.Ingredient;
import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


class IngredientCommandToIngredientTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Long UOM_ID = 2L;

    IngredientCommandToIngredient toIngredient;
    @BeforeEach
    void setUp() {
       toIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testEmptyObject(){

        assertNotNull(toIngredient.convert(new IngredientCommand()));
    }

    @Test
    void testNullObject(){
        assertNull(toIngredient.convert(null));
    }


    @Test
    void convert() {
//        given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setIngDescription(DESCRIPTION);
        command.setAmount(AMOUNT);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        command.setUnitOfMeasure(new UnitOfMeasureToUnitOfMeasureCommand().convert(unitOfMeasure));

//        when
        Ingredient ingredient = toIngredient.convert(command);

//        then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE,ingredient.getId());
        assertEquals(DESCRIPTION,ingredient.getIngDescription());
        assertEquals(AMOUNT,ingredient.getAmount());
        assertEquals(UOM_ID,ingredient.getUnitOfMeasure().getId());


    }
}