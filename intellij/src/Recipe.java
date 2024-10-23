import java.time.LocalTime;
import java.util.Set;

public class Recipe {
    String recipeName;
    Set<Ingredient> ingredientsInRecipe;
    String instructions;
    Set<Ingredient.dietary_tags> recipeTags;
    String recipeDescription;
    LocalTime cookTime;
    int servingSize;

    public Recipe(String recipeName, Set<Ingredient> ingredientsInRecipe,
                  String instructions, Set<Ingredient.dietary_tags> recipeTags,
                  String recipeDescription, LocalTime cookTime, int servingSize){

        this.recipeName = recipeName;
        this.ingredientsInRecipe = ingredientsInRecipe;
        this.instructions = instructions;
        this.recipeTags = recipeTags;
        this.recipeDescription = recipeDescription;
        this.cookTime = cookTime;
        this.servingSize = servingSize;
    }
}
