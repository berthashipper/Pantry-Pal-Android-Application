//import org.json.JSONArray;
//import org.json.JSONObject;

import java.time.Duration;
import java.util.Set;


/**
 * Represents a recipe, including its name, ingredients, instructions, and related metadata.
 */
public class Recipe {
    String recipeName;
    Set<Ingredient> ingredientsInRecipe;
    String instructions;
    Set<Ingredient.dietary_tags> recipeTags;
    String recipeDescription;
    Duration cookTime;
    int servingSize;
    String url;

    /**
     * Constructs a new {@code Recipe} instance with the specified parameters.
     *
     * @param recipeName          The name of the recipe.
     * @param ingredientsInRecipe The set of ingredients used in the recipe.
     * @param instructions        The step-by-step cooking instructions.
     * @param recipeTags          The dietary tags associated with the recipe.
     * @param recipeDescription   A brief description of the recipe.
     * @param cookTime            The duration required to cook the recipe.
     * @param servingSize         The number of servings the recipe provides.
     * @param url                 An optional URL with additional details about the recipe.
     */
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

    /*
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
     */
    /**
     * Retrieves the set of ingredients in the recipe.
     *
     * @return A set of {@code Ingredient} objects representing the recipe's ingredients.
     */
    public Set<Ingredient> getIngredients() {
        return ingredientsInRecipe;
    }



    // toString method to print a recipe
    /**
     * Generates a string representation of the recipe, including its name, description, cook time,
     * serving size, ingredients, and instructions.
     *
     * @return A formatted string containing the recipe details.
     */
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
