package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.Category;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<Category, com.sree.testDrivenDevelopment.domain.Category> {

    //    To work in multi thread environment i have used lombok synchronised annotation
    @Synchronized
    @Override
    public com.sree.testDrivenDevelopment.domain.Category convert(@NonNull Category categoryCommand) {
        final com.sree.testDrivenDevelopment.domain.Category category = new com.sree.testDrivenDevelopment.domain.Category();
        category.setId(categoryCommand.getId());
        category.setCategoryName(categoryCommand.getCategoryName());
        return category; }
}
