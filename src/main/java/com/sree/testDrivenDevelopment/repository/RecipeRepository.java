package com.sree.testDrivenDevelopment.repository;

import com.sree.testDrivenDevelopment.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {
}
