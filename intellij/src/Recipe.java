import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Recipe {
    String recipeName;
    Set<Ingredient> ingredientsInRecipe;
    String instructions;
    Set<Ingredient.dietary_tags> recipeTags;
    String recipeDescription;
    LocalTime cookTime;
    int servingSize;

    // Constructor method for Recipe
    public Recipe(String recipeName, Set<Ingredient> ingredientsInRecipe,
                  String instructions, Set<Ingredient.dietary_tags> recipeTags,
                  String recipeDescription, LocalTime cookTime, int servingSize) {

        this.recipeName = recipeName;
        this.ingredientsInRecipe = ingredientsInRecipe;
        this.instructions = instructions;
        this.recipeTags = recipeTags;
        this.recipeDescription = recipeDescription;
        this.cookTime = cookTime;
        this.servingSize = servingSize;
    }

    // Method to add ingredients to a recipe
    public void addIngredient(Ingredient ingredient) {
        if (ingredientsInRecipe == null) {
            ingredientsInRecipe = new HashSet<>();
        }
        ingredientsInRecipe.add(ingredient);
    }

    // Method to print recipe details
    public void printRecipeDetails() {
        System.out.println("Recipe: " + recipeName);
        System.out.println("Description: " + recipeDescription);
        System.out.println("Cook Time: " + cookTime + " minutes");
        System.out.println("Serves: " + servingSize);
        System.out.println("\nIngredients:");
        for (Ingredient ingredient : ingredientsInRecipe) {
            System.out.println("- " + ingredient.quantity + " " + ingredient.unit + " of " + ingredient.name);
        }

        // Split the instructions by newline and number them
        System.out.println("\nInstructions:");
        String[] steps = instructions.split("\n");
        for (int i = 0; i < steps.length; i++) {
            System.out.println((i + 1) + ". " + steps[i].trim());
        }
    }
}
