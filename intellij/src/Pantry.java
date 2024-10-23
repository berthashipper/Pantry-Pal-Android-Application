import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.EnumSet;

public class Pantry {
    Map<String, Ingredient> ingredientList = new HashMap<>();

    // Method to add ingredient to pantry
    public void add_ingredient(String name, int quantity, String unit, Set<Ingredient.dietary_tags> tags) {
        if (tags == null) {
            tags = new HashSet<>();  // Initialize an empty set if no tags are provided
        }
        ingredientList.put(name, new Ingredient(name, quantity, unit, tags));
        System.out.println("Added " + name + " to pantry.");
    }

    // Method to delete ingredient from pantry
    public void delete_ingredient(String name) {
        Ingredient ing = ingredientList.get(name);
        ingredientList.remove(name);
        System.out.println("Deleted " + ing.name + " from pantry.");
    }

    // Method to edit quantity of ingredient in pantry
    public void edit_ingredient(String name, int quantity) {
        Ingredient ing = ingredientList.get(name);
        ing.quantity = quantity;
        System.out.println("You now have " + ing.quantity + " " + ing.unit + " of " + ing.name + " in pantry.");
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