package com.sree.testDrivenDevelopment.repository;

import com.sree.testDrivenDevelopment.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CategoryRepositoryIT {

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByCategoryName() {
      Optional<Category> optionalCategory= categoryRepository.findByCategoryName("American");
      assertTrue(optionalCategory.isPresent());
      assertEquals("American",optionalCategory.get().getCategoryName());

    }
}