import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a pantry containing a list of ingredients and manages various operations,
 * such as adding, deleting, filtering, and searching for ingredients.
 */
public class Pantry {
    /** A map storing ingredients in the pantry, with their names as keys (in lowercase for consistency). */
    public Map<String, Ingredient> ingredientList = new HashMap<>();
    /** A temporary grocery list storing ingredients to be purchased and their desired quantities. */
    Map<Ingredient, Double> groceryList = new HashMap<>();

    /**
     * Adds an existing {@code Ingredient} object to the pantry.
     *
     * @param ingredient The {@code Ingredient} object to add to the pantry.
     */
    public void add_ingredient(Ingredient ingredient) {
        ingredientList.put(ingredient.name.toLowerCase(), ingredient);
        System.out.println("Added " + ingredient.name + " to pantry.");
    }

    /**
     * Creates and adds a new {@code Ingredient} to the pantry with the specified details.
     *
     * @param name     The name of the ingredient.
     * @param quantity The quantity of the ingredient.
     * @param unit     The unit of measurement for the ingredient.
     * @param tags     A set of dietary tags associated with the ingredient.
     */
    public void add_ingredient(String name, int quantity, String unit, Set<Ingredient.dietary_tags> tags) {
        if (tags == null) {
            tags = new HashSet<>();  // Initialize an empty set if no tags are provided
        }
        Ingredient ingredient = new Ingredient(name, quantity, unit, tags);
        ingredientList.put(name.toLowerCase(), ingredient);
        System.out.println("Added " + name + " to pantry.");
    }


    /**
     * Deletes an ingredient from the pantry by name.
     *
     * @param name The name of the ingredient to delete.
     */
    public void delete_ingredient(String name) {
        Ingredient ing = ingredientList.get(name.toLowerCase());  // Use lowercase for consistency
        if (ing != null) {
            ingredientList.remove(name.toLowerCase());  // Also remove by lowercase name
            System.out.println("Deleted " + ing.name + " from pantry.");
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

    /**
     * Updates the quantity of an existing ingredient in the pantry.
     *
     * @param name        The name of the ingredient to update.
     * @param newQuantity The new quantity to set.
     */
    public void edit_ingredient(String name, int newQuantity) {
        Ingredient ingredient = ingredientList.get(name.toLowerCase());  // Use lowercase for consistency
        if (ingredient != null) {
            ingredient.updateQuantity(newQuantity);
            System.out.println("Updated " + name + " to " + newQuantity + " " + ingredient.unit);
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

    /**
     * Filters the ingredients in the pantry based on a specific dietary tag.
     *
     * @param tag The dietary tag to filter ingredients by.
     * @return A list of ingredients that match the specified dietary tag.
     */
    public List<Ingredient> filter_ingredients_by_tag(Ingredient.dietary_tags tag) {
        List<Ingredient> filteredList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList.values()) {
            if (ingredient.tags.contains(tag)) {
                filteredList.add(ingredient);
            }
        }
        return filteredList;
    }

    /**
     * Prints the ingredients in the pantry that match a specific dietary tag.
     *
     * @param tag The dietary tag to filter and print ingredients by.
     */
    public void printIngredientsByTag(Ingredient.dietary_tags tag) {
        List<Ingredient> filteredIngredients = filter_ingredients_by_tag(tag);
        if (filteredIngredients.isEmpty()) {
            System.out.println("No ingredients found with the tag: " + tag);
        } else {
            System.out.println(tag + " Ingredients in Pantry:");
            for (Ingredient ingredient : filteredIngredients) {
                System.out.println(ingredient.name);
            }
        }
    }


    /**
     * Searches for ingredients in the pantry whose names contain the specified substring.
     *
     * @param name The substring to search for in ingredient names.
     */
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

    /**
     * Adds an ingredient to the grocery list with the specified quantity.
     *
     * @param name     The name of the ingredient to add to the grocery list.
     * @param quantity The quantity to purchase.
     */
    public void addToGroceryList(String name, double quantity) {
        Ingredient ingredient = ingredientList.get(name.toLowerCase()); // Use lowercase for consistency
        if (ingredient != null) {
            groceryList.put(ingredient, quantity);
            System.out.println("Added " + quantity + " " + ingredient.unit + " of " + ingredient.name + " to the grocery list.");
        } else {
            System.out.println("Ingredient " + name + " not found in pantry.");
        }
    }

    /**
     * Prints the contents of the grocery list.
     */
    public void printGroceryList() {
        if (groceryList.isEmpty()) {
            System.out.println("Grocery list is empty.");
        } else {
            System.out.println("Grocery List:");
            for (Map.Entry<Ingredient, Double> entry : groceryList.entrySet()) {
                Ingredient ing = entry.getKey();
                Double qty = entry.getValue();
                System.out.println(ing.name + ": " + qty + " " + ing.unit);
            }
        }
    }

    /**
     * Generates a string representation of the pantry's contents.
     *
     * @return A formatted string listing all ingredients in the pantry, along with their quantities and dietary tags.
     */
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