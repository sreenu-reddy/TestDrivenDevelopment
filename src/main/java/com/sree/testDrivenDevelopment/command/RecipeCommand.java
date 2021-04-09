package com.sree.testDrivenDevelopment.command;
import com.sree.testDrivenDevelopment.domain.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;

    @NotEmpty()
    @Size(min = 3,max = 255)
    private String description;

    @Min(1)
    @Max(999)
    @Positive
    private Integer prepTime;

    @Min(1)
    @Max(999)
    @Positive
    private Integer cookTime;

    @Min(1)
    @Max(99)
    @Positive
    private Integer servings;

    private String source;

    @URL
    private String url;

    @NotBlank
    private String direction;

    private Difficulty difficulty;

    private NotesCommand notes;
    private Set<IngredientCommand> ingredientSet = new HashSet<>();
}
