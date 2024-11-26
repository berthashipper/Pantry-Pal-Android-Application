package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code RecipeBuilder} class provides a flexible and fluent API for building {@link Recipe} objects.
 * It allows the user to set various properties of a recipe, including its name, ingredients, instructions,
 * dietary tags, description, cook time, serving size, and more. Once the recipe has been fully defined,
 * the {@link #build()} method can be used to construct the final {@code Recipe} object.
 */
public class RecipeBuilder implements Serializable {

    /** The name of the recipe. */
    public String recipeName;

    /** A set of ingredients required for the recipe. */
    public Set<Ingredient> ingredients = new HashSet<>();

    /** A list of instructions for preparing the recipe. */
    public List<String> instructions = new ArrayList<>();

    /** A set of dietary tags associated with the recipe. */
    public Set<Ingredient.dietary_tags> tags = new HashSet<>();

    /** A description of the recipe. */
    public String description;

    /** The cook time for the recipe. */
    public Duration cookTime;

    /** The number of servings this recipe provides. */
    public int servingSize;

    /** A URL for the recipe, typically linking to a source or more details. */
    public String url;

    /** The total calorie count for the recipe. */
    public double calories;

    /** The image URL for the recipe. */
    public String imageUrl;

    /**
     * Sets the name of the recipe.
     *
     * @param name The name of the recipe.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder setName(String name) {
        this.recipeName = name;
        return this;
    }

    /**
     * Adds an ingredient to the recipe using an {@link Ingredient} object.
     *
     * @param ingredient The ingredient to add.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    /**
     * Overloaded method to add an ingredient using its name, quantity, unit, and dietary tags.
     *
     * @param name The name of the ingredient.
     * @param quantity The quantity of the ingredient.
     * @param unit The unit of measurement for the ingredient.
     * @param dietaryTags The dietary tags associated with the ingredient.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder addIngredient(String name, int quantity, String unit, Set<Ingredient.dietary_tags> dietaryTags) {
        // Convert dietary_tags to a set of strings
        Set<String> tagStrings = dietaryTags.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        // Create a new Ingredient object and add it to the ingredients set
        Ingredient ingredient = new Ingredient(name, quantity, unit, tagStrings);
        ingredients.add(ingredient);
        return this;
    }

    /**
     * Adds an instruction step to the recipe.
     *
     * @param instruction A step of the recipe's instructions.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder addInstruction(String instruction) {
        instructions.add(instruction);
        return this;
    }

    /**
     * Adds a dietary tag to the recipe.
     *
     * @param tag A dietary tag to add to the recipe.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder addTag(Ingredient.dietary_tags tag) {
        tags.add(tag);
        return this;
    }

    /**
     * Sets the description for the recipe.
     *
     * @param description A description of the recipe.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the cook time for the recipe.
     *
     * @param time The duration of the cook time.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder setCookTime(Duration time) {
        this.cookTime = time;
        return this;
    }

    /**
     * Sets the serving size for the recipe.
     *
     * @param size The number of servings the recipe provides.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder setServingSize(int size) {
        this.servingSize = size;
        return this;
    }

    /**
     * Sets the URL for the recipe, typically linking to a source or more details.
     *
     * @param url The URL for the recipe.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Sets the calorie count for the recipe.
     *
     * @param calories The total calorie count for the recipe.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder setCalories(double calories) {
        this.calories = calories;
        return this;
    }

    /**
     * Sets the image URL for the recipe.
     *
     * @param imageUrl The image URL for the recipe.
     * @return The current {@code RecipeBuilder} instance for method chaining.
     */
    public RecipeBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    /**
     * Builds and returns the final {@link Recipe} object using the specified properties.
     * The instructions are joined into a single string, separated by newlines.
     *
     * @return A new {@code Recipe} object with the specified properties.
     */
    public Recipe build() {
        // Join the instructions into a single string, with each step on a new line
        String joinedInstructions = instructions.stream().collect(Collectors.joining("\n"));

        // Return the constructed Recipe object
        return new Recipe(
                recipeName,
                ingredients,
                joinedInstructions,
                tags,
                description,
                cookTime,
                servingSize,
                url,
                calories,
                imageUrl
        );
    }
}
