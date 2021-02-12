package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class CategoryCommandToCategoryTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CategoryCommandToCategory toCategory;


    @BeforeEach
    void setUp() {
        toCategory = new CategoryCommandToCategory();
    }

    @Test
    void testEmptyCategoryCommand(){
        assertNotNull(toCategory.convert(new Category()));
    }

    @Test
    void convert() {
//        given
        Category categoryCommand = new Category();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setCategoryName(DESCRIPTION);


//        when
        com.sree.testDrivenDevelopment.domain.Category category = toCategory.convert(categoryCommand);

//        then
        assertNotNull(category);
      assertEquals(ID_VALUE,category.getId());
      assertEquals(DESCRIPTION,category.getCategoryName());

    }
}