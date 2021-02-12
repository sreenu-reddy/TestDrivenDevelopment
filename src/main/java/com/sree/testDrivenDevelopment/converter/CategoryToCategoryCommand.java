package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.Category;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<com.sree.testDrivenDevelopment.domain.Category, Category> {
    @Synchronized
    @Override
    public Category convert(@NonNull com.sree.testDrivenDevelopment.domain.Category category) {
        final Category categoryCommand = new Category();
        categoryCommand.setId(category.getId());
        categoryCommand.setCategoryName(category.getCategoryName());
        return categoryCommand; }
}
