package com.example.pantrypalandroidprototype.model;

import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Recipe implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    public String recipeName;
    public Set<Ingredient> ingredientsInRecipe;
    public String instructions;
    public Set<Ingredient.dietary_tags> recipeTags;
    public String recipeDescription;
    public Duration cookTime;
    public int servingSize;
    public String url;
    public double calories;
    public String imageUrl;

    // Constructor for EDAMAM recipes
    public Recipe(String recipeName, String imageUrl, String url) {
        this.recipeName = recipeName;
        this.imageUrl = imageUrl;
        this.url = url;
        this.ingredientsInRecipe = new HashSet<>();
        this.recipeTags = new HashSet<>();
    }


    // General constructor method for Recipe
    public Recipe(String recipeName, Set<Ingredient> ingredientsInRecipe,
                  String instructions, Set<Ingredient.dietary_tags> recipeTags,
                  String recipeDescription, Duration cookTime, int servingSize,
                  String url, double calories, String imageUrl) {
        this.recipeName = recipeName;
        this.ingredientsInRecipe = ingredientsInRecipe;
        this.instructions = instructions;
        this.recipeTags = recipeTags;
        this.recipeDescription = recipeDescription;
        this.cookTime = cookTime;
        this.servingSize = servingSize;
        this.url = url;
        this.calories = calories;
        this.imageUrl = imageUrl;
    }

    // Getter for ingredients
    public Set<Ingredient> getIngredients() {
        return ingredientsInRecipe;
    }

    // Getter for instructions
    public String getInstructions() {
        return instructions;
    }

    // Search method to filter recipes by name
    public static Set<Recipe> filterByName(List<Recipe> recipes, String query) {
        Set<Recipe> filteredRecipes = new HashSet<>();

        if (query == null || query.isEmpty()) {
            return new HashSet<>(recipes);  // If the query is empty, return all recipes
        }

        for (Recipe recipe : recipes) {
            if (recipe.recipeName.toLowerCase().contains(query.toLowerCase())) {
                filteredRecipes.add(recipe);
            }
        }

        return filteredRecipes;
    }

    // Getter for tags
    public Set<Ingredient.dietary_tags>  getTags() {
        return recipeTags;
    }

    // Getter for recipe name
    public String getRecipeName(){
        return recipeName;
    }

    // Getter for recipe url
    public String getUrl(){
        return url;
    }

    // Getter for cook time
    public Duration getCookTime(){
        return cookTime;
    }

    // Getter for serving size
    public int getServingSize(){
        return servingSize;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // toString method to print a recipe
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Recipe: ").append(recipeName).append("\n")
                .append("Description: ").append(recipeDescription).append("\n")
                .append("Cook Time: ").append(cookTime != null ? cookTime.toMinutes() : 0).append(" minutes\n")
                .append("Serves: ").append(servingSize).append("\n")
                .append("Calories: ").append(calories).append(" kcal\n")
                .append("Image: ").append(imageUrl).append("\n")
                .append("URL: ").append(url).append("\n\n")
                .append("Ingredients:\n");

        for (Ingredient ingredient : ingredientsInRecipe) {
            stringBuilder.append("- ").append(ingredient.getQuantity()).append(" ")
                    .append(ingredient.getUnit()).append(" of ")
                    .append(ingredient.getName()).append("\n");
        }

        stringBuilder.append("\nInstructions:\n");
        String[] steps = instructions.split("\n");
        for (int i = 0; i < steps.length; i++) {
            stringBuilder.append((i + 1)).append(". ").append(steps[i].trim()).append("\n");
        }

        return stringBuilder.toString();
    }
}
