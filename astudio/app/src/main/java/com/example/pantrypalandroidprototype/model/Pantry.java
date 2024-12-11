package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a pantry containing a collection of ingredients. Provides methods for managing, filtering,
 * and searching ingredients, as well as managing a grocery list.
 * Implements {@link Serializable} for object serialization.
 */
public class Pantry implements Serializable {

    /** A map to store ingredients, with the ingredient name (in lowercase) as the key. */
    public Map<String, Ingredient> ingredientList = new HashMap<>();

    /** A map representing the grocery list, with ingredients and their desired quantities. */
    public Map<Ingredient, Double> groceryList = new HashMap<>();

    /**
     * Adds an ingredient to the pantry.
     *
     * @param ingredient The {@link Ingredient} object to add.
     */
    public void add_ingredient(Ingredient ingredient) {
        ingredientList.put(ingredient.getName().toLowerCase(), ingredient);
        System.out.println("Added " + ingredient.getName() + " to pantry.");
    }

    /**
     * Adds an ingredient to the pantry using specific details.
     *
     * @param name     The name of the ingredient.
     * @param quantity The quantity of the ingredient.
     * @param unit     The unit of measurement.
     * @param tags     The set of dietary tags associated with the ingredient.
     */
    public void add_ingredient(String name, double quantity, String unit, Set<Ingredient.dietary_tags> tags) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        Set<String> tagStrings = new HashSet<>();
        for (Ingredient.dietary_tags tag : tags) {
            tagStrings.add(tag.name());
        }
        Ingredient ingredient = new Ingredient(name, quantity, unit, tagStrings);
        ingredientList.put(name.toLowerCase(), ingredient);
        System.out.println("Added " + name + " to pantry.");
    }

    /**
     * Deletes an ingredient from the pantry.
     *
     * @param name The name of the ingredient to delete.
     * @return {@code true} if the ingredient was found and deleted; {@code false} otherwise.
     */
    public boolean delete_ingredient(String name) {
        Ingredient ing = ingredientList.get(name.toLowerCase());
        if (ing != null) {
            ingredientList.remove(name.toLowerCase());
            System.out.println("Deleted " + ing.getName() + " from pantry.");
            return true;
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
        return false;
    }

    // Method to clear all ingredients
    public void clear() {
        ingredientList.clear();
    }

    /**
     * Edits the quantity of an existing ingredient in the pantry.
     *
     * @param name        The name of the ingredient.
     * @param newQuantity The new quantity to set.
     * @return {@code true} if the ingredient was found and updated; {@code false} otherwise.
     */
    public boolean edit_ingredient(String name, double newQuantity) {
        Ingredient ingredient = ingredientList.get(name.toLowerCase());
        if (ingredient != null) {
            ingredient.updateQuantity(newQuantity);
            System.out.println("Updated " + name + " to " + newQuantity + " " + ingredient.getUnit());
            return true;
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
        return false;
    }

    /**
     * Filters ingredients in the pantry based on a dietary tag.
     *
     * @param tag The dietary tag to filter by.
     * @return A list of ingredients matching the specified tag.
     */
    public List<Ingredient> filter_ingredients_by_tag(Ingredient.dietary_tags tag) {
        List<Ingredient> filteredList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList.values()) {
            if (ingredient.getTags().contains(tag.name())) {
                filteredList.add(ingredient);
            }
        }
        return filteredList;
    }

    /**
     * Prints ingredients in the pantry that match a specific dietary tag.
     *
     * @param tag The dietary tag to filter by.
     */
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

    /**
     * Searches for ingredients by name.
     *
     * @param name The name (or part of the name) to search for.
     * @return A list of ingredients that match the search query.
     */
    public List<Ingredient> searchIngredient(String name) {
        List<Ingredient> foundIngredients = new ArrayList<>();
        for (String key : ingredientList.keySet()) {
            if (key.toLowerCase().contains(name.toLowerCase())) {
                foundIngredients.add(ingredientList.get(key));
            }
        }
        return foundIngredients;
    }

    /**
     * Adds an ingredient to the grocery list with a specified quantity.
     *
     * @param name     The name of the ingredient.
     * @param quantity The quantity to add to the grocery list.
     */
    public void addToGroceryList(String name, double quantity) {
        Ingredient ingredient = ingredientList.get(name.toLowerCase());
        if (ingredient != null) {
            groceryList.put(ingredient, quantity);
            System.out.println("Added " + quantity + " " + ingredient.getUnit() + " of " + ingredient.getName() + " to the grocery list.");
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

    public void addIngredientToGroceryList(Ingredient ingredient) {
        groceryList.put(ingredient, ingredient.getQuantity());
    }

    public Map<Ingredient, Double> getGroceryList() {
        Map<Ingredient, Double> groceryList = new HashMap<>();
        for (Ingredient ingredient : this.ingredientList.values()) {
            groceryList.put(ingredient, ingredient.getQuantity());
        }
        return groceryList;
    }

    public void setGroceryList(Map<Ingredient, Double> groceryList) {
        this.groceryList = groceryList;
    }

    /**
     * Prints the grocery list.
     */
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

    /**
     * Retrieves the number of items in the pantry.
     *
     * @return The number of ingredients in the pantry.
     */
    public int getNPantryItems() {
        return this.ingredientList.size();
    }

    public Ingredient getIngredient(String ingredientName) {
        return ingredientList.get(ingredientName.toLowerCase());
    }

    /**
     * Checks if an ingredient exists in the pantry.
     *
     * @param name The name of the ingredient to check.
     * @return {@code true} if the ingredient exists; {@code false} otherwise.
     */
    public boolean has_ingredient(String name) {
        return ingredientList.containsKey(name.toLowerCase());
    }

    /**
     * Retrieves all ingredients in the pantry.
     *
     * @return A list of all ingredients in the pantry.
     */
    public List<Ingredient> getAllIngredients() {
        return new ArrayList<>(ingredientList.values());
    }

    public Ingredient getIngredientByName(String name) {
        for (Ingredient ingredient : ingredientList.values()) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null; // Return null if the ingredient is not found
    }

    /**
     * Returns a string representation of the pantry contents.
     *
     * @return A formatted string containing all ingredients and their details.
     */
    @Override
    public String toString() {
        StringBuilder pantryContents = new StringBuilder("🛒 Pantry Contents:\n\n");

        if (ingredientList.isEmpty()) {
            pantryContents.append("Your pantry is currently empty. Add some ingredients to get started!\n");
        } else {
            for (Map.Entry<String, Ingredient> entry : ingredientList.entrySet()) {
                Ingredient ing = entry.getValue();
                pantryContents.append("• ").append(ing.getName()).append("\n")
                        .append("   Quantity: ").append(ing.getQuantity()).append(" ").append(ing.getUnit()).append("\n")
                        .append("   Tags: ").append(ing.getTags().isEmpty() ? "None" : String.join(", ", ing.getTags())).append("\n\n");
            }
        }
        return pantryContents.toString();
    }
}
