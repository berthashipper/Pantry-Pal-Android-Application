import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.EnumSet;

public class Pantry {
    public Map<String, Ingredient> ingredientList = new HashMap<>();
    // Temporary grocery list since that use case hasn't been implemented yet
    Map<Ingredient, Integer> groceryList = new HashMap<>();

    // Method to add an existing Ingredient object to the pantry
    public void add_ingredient(Ingredient ingredient) {
        ingredientList.put(ingredient.name.toLowerCase(), ingredient);
        System.out.println("Added " + ingredient.name + " to pantry.");
    }

    // Overloaded method to create AND add an Ingredient
    public void add_ingredient(String name, int quantity, String unit, Set<Ingredient.dietary_tags> tags) {
        if (tags == null) {
            tags = new HashSet<>();  // Initialize an empty set if no tags are provided
        }
        Ingredient ingredient = new Ingredient(name, quantity, unit, tags);
        ingredientList.put(name.toLowerCase(), ingredient);
        System.out.println("Added " + name + " to pantry.");
    }

    // Method to delete ingredient from pantry
    public void delete_ingredient(String name) {
        Ingredient ing = ingredientList.get(name.toLowerCase());  // Use lowercase for consistency
        if (ing != null) {
            ingredientList.remove(name.toLowerCase());  // Also remove by lowercase name
            System.out.println("Deleted " + ing.name + " from pantry.");
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

    // Method to edit quantity of ingredient in pantry
    public void edit_ingredient(String name, int newQuantity) {
        Ingredient ingredient = ingredientList.get(name.toLowerCase());  // Use lowercase for consistency
        if (ingredient != null) {
            ingredient.updateQuantity(newQuantity);
            System.out.println("Updated " + name + " to " + newQuantity + " " + ingredient.unit);
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

    // Method to filter ingredients by dietary tag
    public List<Ingredient> filter_ingredients_by_tag(Ingredient.dietary_tags tag) {
        List<Ingredient> filteredList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList.values()) {
            if (ingredient.tags.contains(tag)) {
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
                System.out.println(ingredient);
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
    public void addToGroceryList(String name, int quantity) {
        Ingredient ingredient = ingredientList.get(name.toLowerCase()); // Use lowercase for consistency
        if (ingredient != null) {
            groceryList.put(ingredient, quantity);
            System.out.println("Added " + quantity + " " + ingredient.unit + " of " + ingredient.name + " to the grocery list.");
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
            for (Map.Entry<Ingredient, Integer> entry : groceryList.entrySet()) {
                Ingredient ing = entry.getKey();
                int qty = entry.getValue();
                System.out.println(ing.name + ": " + qty + " " + ing.unit);
            }
        }
    }


    // toString method to print pantry contents
    @Override
    public String toString() {
        StringBuilder pantryContents = new StringBuilder("Pantry contents:\n");
        for (Map.Entry<String, Ingredient> entry : ingredientList.entrySet()) {
            Ingredient ing = entry.getValue();
            pantryContents.append(ing.name).append(": ").append(ing.quantity).append(" ").append(ing.unit).append(", Tags: ").append(ing.tags).append("\n");
        }
        return pantryContents.toString();
    }
}