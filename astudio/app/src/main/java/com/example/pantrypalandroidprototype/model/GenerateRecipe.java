package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class GenerateRecipe implements Serializable {
    Pantry userPantry;
    Set<Recipe> allRecipes;
    static final int SIMILARITY_THRESHOLD = 3;

    // Constructor
    public GenerateRecipe(Pantry userPantry, Set<Recipe> allRecipes) {
        this.userPantry = userPantry;
        this.allRecipes = allRecipes;
    }

    // Main method to generate matching recipes based on pantry ingredients
    public Set<Recipe> generateMatchingRecipes() {
        Set<Recipe> matchedRecipes = new HashSet<>();

        for (Recipe recipe : allRecipes) {
            if (canMakeRecipe(recipe)) {
                matchedRecipes.add(recipe);
            }
        }

        return matchedRecipes;
    }

    // Helper method to check if the pantry has enough of all ingredients to make the recipe
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