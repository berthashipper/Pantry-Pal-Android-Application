package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GenerateRecipe implements Serializable {
    Pantry userPantry;
    Set<Recipe> allRecipes;

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

    // Method to print matched recipes with numbering
    private void printMatchedRecipes(Set<Recipe> matchedRecipes) {
        if (matchedRecipes.isEmpty()) {
            System.out.println("No matching recipes found.");
        } else {
            int index = 1;
            System.out.println("Matched Recipes to your Pantry:");
            for (Recipe recipe : matchedRecipes) {
                System.out.println(index + ". " + recipe.recipeName);
                index++;
            }
        }
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
        // First, check if an exact match exists
        Ingredient exactMatch = userPantry.ingredientList.get(ingredientName.toLowerCase());
        if (exactMatch != null) {
            return exactMatch;
        }

        // If no exact match, try to match partial or synonymous names (e.g., "peppers" and "bell peppers")
        for (Map.Entry<String, Ingredient> entry : userPantry.ingredientList.entrySet()) {
            String pantryIngredientName = entry.getKey();
            // Simple partial match (could be enhanced with more sophisticated matching logic)
            if (pantryIngredientName.contains(ingredientName)) {
                return entry.getValue();
            }
        }

        // Return null if no matching ingredient is found
        return null;
    }

    private Map<String, Set<String>> ingredientSynonyms = new HashMap<String, Set<String>>() {{
        put("peppers", new HashSet<>(Arrays.asList("bell peppers", "chili peppers", "sweet peppers")));
        put("tomatoes", new HashSet<>(Arrays.asList("cherry tomatoes", "roma tomatoes", "tomato")));
        // Add more mappings as needed
    }};

    /* Drafting for next iteration
    public void generateGroceryList (Recipe recipe) {
        Map<String, Ingredient> pantryIngredients = userPantry.ingredientList; //ingredients in pantry

            if (!canMakeRecipe(recipe)) {
                Set<Ingredient> inRecipe = recipe.ingredientsInRecipe;
                for (Ingredient ingredient : inRecipe) {
                    Ingredient pantryIngredient = pantryIngredients.get(ingredient.name.toLowerCase());
                    if (pantryIngredient == null) {
                        userPantry.addToGroceryList(ingredient.name.toLowerCase(), ingredient.quantity);
                    } else if(pantryIngredient.quantity < ingredient.quantity) {
                            double count = ingredient.quantity - pantryIngredient.quantity;
                            userPantry.addToGroceryList(ingredient.name.toLowerCase(), count);
                            System.out.println("Added " + ingredient.name.toLowerCase() + " to grocery list.");
                        }

            }
        }


    }*/
}

