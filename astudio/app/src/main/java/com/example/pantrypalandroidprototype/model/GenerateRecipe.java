package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
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
    private boolean canMakeRecipe(Recipe recipe) {
        for (Ingredient recipeIngredient : recipe.getIngredients()) {
            Ingredient pantryIngredient = userPantry.ingredientList.get(recipeIngredient.getName().toLowerCase());

            // Check if the pantry has the ingredient and enough quantity
            if (pantryIngredient == null || pantryIngredient.getQuantity() < recipeIngredient.getQuantity()) {
                return false; // Missing ingredient or not enough quantity
            }
        }
        return true; // All ingredients are available in sufficient quantity
    }

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

