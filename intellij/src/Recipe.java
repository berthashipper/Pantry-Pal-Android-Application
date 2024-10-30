//import org.json.JSONArray;
//import org.json.JSONObject;

import java.time.Duration;
import java.util.Set;

public class Recipe {
    String recipeName;
    Set<Ingredient> ingredientsInRecipe;
    String instructions;
    Set<Ingredient.dietary_tags> recipeTags;
    String recipeDescription;
    Duration cookTime;
    int servingSize;
    String url;

    // Constructor method for Recipe
    public Recipe(String recipeName, Set<Ingredient> ingredientsInRecipe,
                  String instructions, Set<Ingredient.dietary_tags> recipeTags,
                  String recipeDescription, Duration cookTime, int servingSize, String url) {
        this.recipeName = recipeName;
        this.ingredientsInRecipe = ingredientsInRecipe;
        this.instructions = instructions;
        this.recipeTags = recipeTags;
        this.recipeDescription = recipeDescription;
        this.cookTime = cookTime;
        this.servingSize = servingSize;
        this.url = url; // Initialize URL
    }

    // Method to print recipe details
    public void printRecipeDetails() {
        System.out.println("Recipe: " + recipeName);
        System.out.println("Description: " + recipeDescription);
        System.out.println("Cook Time: " + (cookTime != null ? cookTime.toMinutes() : 0) + " minutes");
        System.out.println("Serves: " + servingSize);
        System.out.println("\nIngredients:");

        // Print each ingredient with its details
        for (Ingredient ingredient : ingredientsInRecipe) {
            System.out.println("- " + ingredient.getQuantity() + " " + ingredient.getUnit() + " of " + ingredient.getName());
        }

        // Print the instructions
        System.out.println("\nInstructions:");
        String[] steps = instructions.split("\n");
        for (int i = 0; i < steps.length; i++) {
            System.out.println((i + 1) + ". " + steps[i].trim());
        }
    }

    // Getter for ingredients
    public Set<Ingredient> getIngredients() {
        return ingredientsInRecipe;
    }

    // toString method to print a recipe
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Recipe: ").append(recipeName).append("\n")
                .append("Description: ").append(recipeDescription).append("\n")
                .append("Cook Time: ").append(cookTime != null ? cookTime.toMinutes() : 0).append(" minutes\n")
                .append("Serves: ").append(servingSize).append("\n\n")
                .append("Ingredients:\n");

        // Print each ingredient with its details
        for (Ingredient ingredient : ingredientsInRecipe) {
            stringBuilder.append("- ").append(ingredient.getQuantity()).append(" ")
                    .append(ingredient.getUnit()).append(" of ")
                    .append(ingredient.getName()).append("\n");
        }

        // Print the instructions
        stringBuilder.append("\nInstructions:\n");
        String[] steps = instructions.split("\n");
        for (int i = 0; i < steps.length; i++) {
            stringBuilder.append((i + 1)).append(". ").append(steps[i].trim()).append("\n");
        }

        return stringBuilder.toString();
    }
}
