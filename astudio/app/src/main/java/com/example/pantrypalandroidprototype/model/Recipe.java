package com.example.pantrypalandroidprototype.model;

import android.util.Log;

import java.io.Serializable;
import java.time.Duration;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

/**
 * Represents a recipe with various attributes such as ingredients, instructions, tags, and nutritional information.
 * Implements {@link Serializable} for object serialization.
 */
public class Recipe implements Serializable {
    /** Unique identifier for the recipe. */
    //@PrimaryKey(autoGenerate = true)
    private int id;

    /** The name of the recipe. */
    public String recipeName;

    /** The set of ingredients used in the recipe. */
    public Set<Ingredient> ingredientsInRecipe;

    /** The cooking instructions for the recipe. */
    public String instructions;

    /** The set of dietary tags associated with the recipe. */
    //public Set<Ingredient.dietary_tags> recipeTags;
    public Set<String> recipeTagsStrings;
    public final Set<String> dynamicTags;

    /** A brief description of the recipe. */
    public String recipeDescription;

    /** The time required to cook the recipe. */
    public Duration cookTime;

    /** The number of servings the recipe makes. */
    public int servingSize;

    /** The URL for additional recipe details or reference. */
    public String url;

    /** The total calorie content of the recipe. */
    public double calories;

    /** The URL of the image representing the recipe. */
    public String imageUrl;

    /**
     * Constructor for creating a {@code Recipe} object with basic details from EDAMAM recipes.
     *
     * @param recipeName The name of the recipe.
     * @param imageUrl   The URL of the recipe image.
     * @param url        The URL for more details about the recipe.
     */
    public Recipe(String recipeName, String imageUrl, String url) {
        this.recipeName = recipeName;
        this.imageUrl = imageUrl;
        this.url = url;
        this.ingredientsInRecipe = new HashSet<>();
        this.recipeTagsStrings = new HashSet<>();
        this.dynamicTags = new HashSet<>();
    }

    /**
     * General constructor for creating a {@code Recipe} object with all details.
     *
     * @param recipeName          The name of the recipe.
     * @param ingredientsInRecipe The set of ingredients used in the recipe.
     * @param instructions        The cooking instructions.
     * @param recipeTags          The set of dietary tags.
     * @param recipeDescription   A brief description of the recipe.
     * @param cookTime            The cooking time.
     * @param servingSize         The number of servings.
     * @param url                 The URL for more details.
     */
    public Recipe(String recipeName, Set<Ingredient> ingredientsInRecipe,
                  String instructions, Set<String> recipeTags,
                  String recipeDescription, Duration cookTime, int servingSize, String url) {
        this.recipeName = recipeName;
        this.ingredientsInRecipe = ingredientsInRecipe;
        this.instructions = instructions;
        this.recipeTagsStrings = recipeTags;
        this.dynamicTags = new HashSet<>();
        this.recipeDescription = recipeDescription;
        this.cookTime = cookTime;
        this.servingSize = servingSize;
        this.url = url;
    }


    /**
     * Retrieves the set of ingredients in the recipe.
     *
     * @return A set of {@link Ingredient} objects.
     */
    public Set<Ingredient> getIngredients() {
        return ingredientsInRecipe;
    }

    /**
     * Retrieves the cooking instructions for the recipe.
     *
     * @return The instructions as a {@code String}.
     */
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Filters a list of recipes based on a query string contained in the recipe name.
     *
     * @param recipes A list of recipes to filter.
     * @param query   The search query.
     * @return A set of recipes whose names contain the query string.
     */
    public static Set<Recipe> filterByName(List<Recipe> recipes, String query) {
        Set<Recipe> filteredRecipes = new HashSet<>();

        if (query == null || query.isEmpty()) {
            return new HashSet<>(recipes);  // Return all recipes if query is empty
        }

        for (Recipe recipe : recipes) {
            if (recipe.recipeName.toLowerCase().contains(query.toLowerCase())) {
                filteredRecipes.add(recipe);
            }
        }

        return filteredRecipes;
    }

    /**
     * Retrieves the dietary tags associated with the recipe.
     *
     * @return A set of dietary tags.
     */
    public Set<String> getTags() {
        return new HashSet<>(recipeTagsStrings);
    }

    public void addTag(String tag) {
        if (recipeTagsStrings == null) {
            recipeTagsStrings = new HashSet<>();
        }
        recipeTagsStrings.add(tag);
    }

    /**
     * Adds a new dynamic tag to the recipe.
     *
     * @param tag The dynamic tag to be added.
     */
    public void addDynamicTag(String tag) {
        dynamicTags.add(tag);
    }

    /**
     * Retrieves the set of dietary tags associated with the recipe.
     *
     * @return A {@link Set} of dietary tag strings.
     */
    public Set<String> getDietaryTags() {
        return recipeTagsStrings;
    }

    /**
     * Retrieves the set of dynamic tags associated with the recipe.
     *
     * @return A {@link Set} of dynamic tag strings.
     */
    public Set<String> getDynamicTags() {
        return recipeTagsStrings;
    }

    /**
     * Removes a specified tag from the recipe.
     *
     * @param tag The tag to be removed.
     * @return {@code true} if the tag was successfully removed, {@code false} if the tag was not found.
     */
    public boolean removeTag(String tag) {
        if (recipeTagsStrings.contains(tag)) {
            recipeTagsStrings.remove(tag);
            dynamicTags.remove(tag);
            Log.d("Recipe", "Tag removed from recipe: " + tag);
            return true;
        }
        Log.e("Recipe", "Tag not found: " + tag);
        return false;
    }

    /**
     * Retrieves the name of the recipe.
     *
     * @return The recipe name.
     */
    public String getRecipeName() {
        return recipeName;
    }

    /**
     * Retrieves the description of the recipe.
     *
     * @return The description name.
     */
    public String getRecipeDescription() {
        return recipeDescription;
    }

    /**
     * Retrieves the URL for additional recipe details.
     *
     * @return The URL as a {@code String}.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Retrieves the cooking time for the recipe.
     *
     * @return The cooking time as a {@link Duration}.
     */
    public Duration getCookTime() {
        return cookTime;
    }

    public void setCookTime(Duration cookTime) {
        this.cookTime = cookTime;
    }

    /**
     * Retrieves the serving size for the recipe.
     *
     * @return The number of servings.
     */
    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    /**
     * Retrieves the total calorie content of the recipe.
     *
     * @return The calorie count.
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Sets the calorie content of the recipe.
     *
     * @param calories The calorie count to set.
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * Retrieves the image URL for the recipe.
     *
     * @return The image URL.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the image URL for the recipe.
     *
     * @param imageUrl The image URL to set.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    /**
     * Returns a string representation of the recipe, including all attributes and details.
     *
     * @return A formatted string containing recipe details.
     */
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
