package guru.springframework.recipe.app.commands;

import guru.springframework.recipe.app.domain.Category;
import guru.springframework.recipe.app.domain.Difficulty;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.Note;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String description;
    @Min(5)
    @Max(200)
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String direction;
    private Difficulty difficulty;
    private NoteCommand note;
    private Byte[] img;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
}
