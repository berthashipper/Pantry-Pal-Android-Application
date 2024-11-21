package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.util.*;

public class Pantry implements Serializable {
    public Map<String, Ingredient> ingredientList = new HashMap<>();
    Map<Ingredient, Double> groceryList = new HashMap<>();

    public void add_ingredient(Ingredient ingredient) {
        ingredientList.put(ingredient.getName().toLowerCase(), ingredient);
        System.out.println("Added " + ingredient.getName() + " to pantry.");
    }

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

    public List<Ingredient> filter_ingredients_by_tag(Ingredient.dietary_tags tag) {
        List<Ingredient> filteredList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList.values()) {
            if (ingredient.getTags().contains(tag.name())) {
                filteredList.add(ingredient);
            }
        }
        return filteredList;
    }

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

    public List<Ingredient> searchIngredient(String name) {
        List<Ingredient> foundIngredients = new ArrayList<>();
        for (String key : ingredientList.keySet()) {
            if (key.toLowerCase().contains(name.toLowerCase())) {
                foundIngredients.add(ingredientList.get(key));
            }
        }
        return foundIngredients; // Return the list of found ingredients
    }

    public void addToGroceryList(String name, double quantity) {
        Ingredient ingredient = ingredientList.get(name.toLowerCase());
        if (ingredient != null) {
            groceryList.put(ingredient, quantity);
            System.out.println("Added " + quantity + " " + ingredient.getUnit() + " of " + ingredient.getName() + " to the grocery list.");
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

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

    public int getNPantryItems() {
        return this.ingredientList.size();
    }

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

    public boolean has_ingredient(String name) {
        return ingredientList.containsKey(name.toLowerCase());
    }

    public List<Ingredient> getAllIngredients() {
        return new ArrayList<>(ingredientList.values());
    }

}
