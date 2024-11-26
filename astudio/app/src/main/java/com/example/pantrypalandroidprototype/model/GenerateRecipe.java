package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.text.similarity.LevenshteinDistance;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * The {@code GenerateRecipe} class is responsible for generating recipes that can be made from a user's pantry.
 * It compares the ingredients available in the user's pantry against the ingredients required by the recipes,
 * and returns a set of recipes that can be made based on the available ingredients.
 * This class also includes functionality for handling ingredient matching with a similarity threshold.
 */
public class GenerateRecipe implements Serializable {

    /** The user's pantry containing available ingredients. */
    Pantry userPantry;

    /** The set of all available recipes. */
    Set<Recipe> allRecipes;

    /** The similarity threshold for ingredient matching. */
    static final int SIMILARITY_THRESHOLD = 3;

    /**
     * Constructs a {@code GenerateRecipe} instance with the user's pantry and all available recipes.
     *
     * @param userPantry The user's pantry containing available ingredients.
     * @param allRecipes The set of all available recipes.
     */
    public GenerateRecipe(Pantry userPantry, Set<Recipe> allRecipes) {
        this.userPantry = userPantry;
        this.allRecipes = allRecipes;
    }

    /**
     * Generates a set of recipes that can be made using the ingredients in the user's pantry.
     *
     * @return A set of recipes that can be made based on the available ingredients in the pantry.
     */
    public Set<Recipe> generateMatchingRecipes() {
        Set<Recipe> matchedRecipes = new HashSet<>();

        for (Recipe recipe : allRecipes) {
            if (canMakeRecipe(recipe)) {
                matchedRecipes.add(recipe);
            }
        }

        return matchedRecipes;
    }

    /**
     * Checks if the user's pantry has enough of all required ingredients to make the specified recipe.
     *
     * @param recipe The recipe to check.
     * @return {@code true} if the recipe can be made with the available ingredients in the pantry,
     *         {@code false} otherwise.
     */
    public boolean canMakeRecipe(Recipe recipe) {
        for (Ingredient recipeIngredient : recipe.getIngredients()) {
            Ingredient pantryIngredient = findMatchingPantryIngredient(recipeIngredient.getName().toLowerCase());

            // If no matching ingredient is found or the quantity is insufficient, return false
            if (pantryIngredient == null || pantryIngredient.getQuantity() < recipeIngredient.getQuantity()) {
                return false; // Missing ingredient or not enough quantity
            }
        }
        return true; // All ingredients are available in sufficient quantity
    }

    /**
     * Finds the best matching ingredient from the pantry for a given recipe ingredient name.
     * It checks for exact matches first and then performs a similarity check using token overlap and Levenshtein distance.
     *
     * @param ingredientName The name of the ingredient from the recipe to find in the pantry.
     * @return The best matching ingredient from the pantry, or {@code null} if no match is found.
     */
    public Ingredient findMatchingPantryIngredient(String ingredientName) {
        // Normalize recipe ingredient name to lowercase and split into tokens
        String[] recipeTokens = ingredientName.toLowerCase().split("\\s+");

        // First, check for an exact match
        Ingredient exactMatch = userPantry.ingredientList.get(ingredientName.toLowerCase());
        if (exactMatch != null) {
            return exactMatch;
        }

        // Use enhanced similarity matching
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        Ingredient bestMatch = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Map.Entry<String, Ingredient> entry : userPantry.ingredientList.entrySet()) {
            String pantryIngredientName = entry.getKey().toLowerCase();
            String[] pantryTokens = pantryIngredientName.split("\\s+");

            // Token overlap check
            long tokenOverlap = Arrays.stream(recipeTokens)
                    .filter(token -> Arrays.asList(pantryTokens).contains(token))
                    .count();

            // If significant token overlap exists, consider it a match
            if (tokenOverlap > 0) {
                return entry.getValue();
            }

            // Otherwise, calculate Levenshtein distance
            int distance = levenshtein.apply(pantryIngredientName, ingredientName);
            if (distance < bestDistance && distance <= SIMILARITY_THRESHOLD) {
                bestMatch = entry.getValue();
                bestDistance = distance;
            }
        }

        // Return the best match if found
        return bestMatch;
    }
}
