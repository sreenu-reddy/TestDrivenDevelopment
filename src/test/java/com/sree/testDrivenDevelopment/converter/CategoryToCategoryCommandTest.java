package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CategoryToCategoryCommand toCategoryCommand;


    @BeforeEach
    void setUp() {
        toCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    void testEmptyObject(){
        assertNotNull(toCategoryCommand.convert(new com.sree.testDrivenDevelopment.domain.Category()));
    }

    @Test
    void convert() {
//        Given
        com.sree.testDrivenDevelopment.domain.Category category = new com.sree.testDrivenDevelopment.domain.Category();
        category.setId(ID_VALUE);
        category.setCategoryName(DESCRIPTION);
//        when
        Category command = toCategoryCommand.convert(category);

//        Then
        assertNotNull(command);
        assertEquals(ID_VALUE,command.getId());
        assertEquals(DESCRIPTION,command.getCategoryName());
    }
}