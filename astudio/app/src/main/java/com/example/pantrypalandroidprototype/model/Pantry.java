package com.example.pantrypalandroidprototype.model;

import java.util.*;

public class Pantry {
    public Map<String, Ingredient> ingredientList = new HashMap<>();
    // Temporary grocery list since that use case hasn't been implemented yet
    Map<Ingredient, Double> groceryList = new HashMap<>();

    // Method to add an existing Ingredient object to the pantry
    public void add_ingredient(Ingredient ingredient) {
        ingredientList.put(ingredient.getName().toLowerCase(), ingredient);
        System.out.println("Added " + ingredient.getName() + " to pantry.");
    }

    // Overloaded method to create AND add an Ingredient
    public void add_ingredient(String name, double quantity, String unit, Set<Ingredient.dietary_tags> tags) {
        if (tags == null) {
            tags = new HashSet<>();  // Initialize an empty set if no tags are provided
        }
        // Convert DietaryTags enum to strings if needed
        Set<String> tagStrings = new HashSet<>();
        for (Ingredient.dietary_tags tag : tags) {
            tagStrings.add(tag.name());
        }
        Ingredient ingredient = new Ingredient(name, quantity, unit, tagStrings);
        ingredientList.put(name.toLowerCase(), ingredient);
        System.out.println("Added " + name + " to pantry.");
    }

    // Method to delete ingredient from pantry
    public void delete_ingredient(String name) {
        Ingredient ing = ingredientList.get(name.toLowerCase());  // Use lowercase for consistency
        if (ing != null) {
            ingredientList.remove(name.toLowerCase());  // Also remove by lowercase name
            System.out.println("Deleted " + ing.getName() + " from pantry.");
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

    // Method to edit quantity of ingredient in pantry
    public void edit_ingredient(String name, double newQuantity) {
        Ingredient ingredient = ingredientList.get(name.toLowerCase());  // Use lowercase for consistency
        if (ingredient != null) {
            ingredient.updateQuantity(newQuantity);
            System.out.println("Updated " + name + " to " + newQuantity + " " + ingredient.getUnit());
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

    // Method to filter ingredients by dietary tag
    public List<Ingredient> filter_ingredients_by_tag(Ingredient.dietary_tags tag) {
        List<Ingredient> filteredList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList.values()) {
            if (ingredient.getTags().contains(tag.name())) {
                filteredList.add(ingredient);
            }
        }
        return filteredList;
    }

    // Method to print ingredients filtered by dietary tag
    public void printIngredientsByTag(Ingredient.dietary_tags tag) {
        List<Ingredient> filteredIngredients = filter_ingredients_by_tag(tag);
        if (filteredIngredients.isEmpty()) {
            System.out.println("No ingredients found with the tag: " + tag);
        } else {
            System.out.println(tag + " Ingredients in Pantry:");
            for (Ingredient ingredient : filteredIngredients) {
                System.out.println(ingredient.getName());
            }
        }
    }

    // Method to search for ingredients by name substring
    public void searchIngredient(String name) {
        List<Ingredient> foundIngredients = new ArrayList<>();
        for (String key : ingredientList.keySet()) {
            if (key.toLowerCase().contains(name.toLowerCase())) {
                foundIngredients.add(ingredientList.get(key));
            }
        }
        if (foundIngredients.isEmpty()) {
            System.out.println("Ingredient " + name + " not found in the pantry.");
        } else {
            for (Ingredient ingredient : foundIngredients) {
                System.out.println(ingredient);
            }
        }
    }

    // Method to add ingredient to grocery list
    public void addToGroceryList(String name, double quantity) {
        Ingredient ingredient = ingredientList.get(name.toLowerCase()); // Use lowercase for consistency
        if (ingredient != null) {
            groceryList.put(ingredient, quantity);
            System.out.println("Added " + quantity + " " + ingredient.getUnit() + " of " + ingredient.getName() + " to the grocery list.");
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

    // Method to print grocery list
    public void printGroceryList() {
        if (groceryList.isEmpty()) {
            System.out.println("Grocery list is empty.");
        } else {
            System.out.println("Grocery List:");
            for (Map.Entry<Ingredient, Double> entry : groceryList.entrySet()) {
                Ingredient ing = entry.getKey();
                Double qty = entry.getValue();
                System.out.println(ing.getName() + ": " + qty + " " + ing.getUnit());
            }
        }
    }

    public int getNPantryItems() { return this.ingredientList.size(); }

    // toString method to print pantry contents
    @Override
    public String toString() {
        StringBuilder pantryContents = new StringBuilder("Pantry contents:\n");
        for (Map.Entry<String, Ingredient> entry : ingredientList.entrySet()) {
            Ingredient ing = entry.getValue();
            pantryContents.append(ing.getName()).append(": ").append(ing.getQuantity()).append(" ").append(ing.getUnit()).append(", Tags: ").append(ing.getTags()).append("\n");
        }
        return pantryContents.toString();
    }
}
