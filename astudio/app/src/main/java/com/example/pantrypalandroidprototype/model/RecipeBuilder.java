package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeBuilder implements Serializable {
    private String recipeName;
    private Set<Ingredient> ingredients = new HashSet<>();
    private List<String> instructions = new ArrayList<>();  // Store instructions in a list
    private Set<Ingredient.dietary_tags> tags = new HashSet<>();
    private String description;
    private Duration cookTime;
    private int servingSize;
    private String url;

    // Set the recipe name
    public RecipeBuilder setName(String name) {
        this.recipeName = name;
        return this;
    }

    // Add an ingredient using Ingredient object
    public RecipeBuilder addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    // Overloaded method to add an ingredient using name, quantity, unit, and dietary tags
    public RecipeBuilder addIngredient(String name, int quantity, String unit, Set<Ingredient.dietary_tags> dietaryTags) {
        // Convert dietary_tags to a set of strings
        Set<String> tagStrings = dietaryTags.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        // Pass the converted tags to the Ingredient constructor
        Ingredient ingredient = new Ingredient(name, quantity, unit, tagStrings);
        ingredients.add(ingredient);
        return this;
    }

    // Add a step to the instructions
    public RecipeBuilder addInstruction(String instruction) {
        instructions.add(instruction);  // Append the instruction to the list
        return this;
    }

    // Add a dietary tag
    public RecipeBuilder addTag(Ingredient.dietary_tags tag) {
        tags.add(tag);
        return this;
    }

    // Set the description
    public RecipeBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    // Set the cook time
    public RecipeBuilder setCookTime(Duration time) {
        this.cookTime = time;
        return this;
    }

    // Set the serving size
    public RecipeBuilder setServingSize(int size) {
        this.servingSize = size;
        return this;
    }

    // Add a method to set the URL
    public RecipeBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    // Build the recipe
    public Recipe build() {
        // Present instructions in a visually appealing way, separated by new lines
        String joinedInstructions = instructions.stream().collect(Collectors.joining("\n"));

        // Create and return the Recipe object
        return new Recipe(recipeName, ingredients, joinedInstructions, tags, description, cookTime, servingSize, url);
    }
}
