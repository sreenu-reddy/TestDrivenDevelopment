package com.sree.testDrivenDevelopment.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class NotesCommand {
    private Long id;
    @NotBlank
    private String recipeNotes;
}
