package com.sree.testDrivenDevelopment.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"recipeSet"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    //Recipe
    @ManyToMany(mappedBy = "categorySet")
    private Set<Recipe> recipeSet = new HashSet<>();

    public Category() {
    }
}
