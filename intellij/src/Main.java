import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Pantry pantry = new Pantry();

        // Adding ingredients with dietary tags
        pantry.add_ingredient("Butter", 10, "tablespoons", EnumSet.of(Ingredient.dietary_tags.VEGETARIAN));
        pantry.add_ingredient("White Bread", 20, "slices", EnumSet.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.VEGETARIAN));
        pantry.add_ingredient("Mozzarella Cheese", 1, "pack",null);
        pantry.add_ingredient("Cheddar Cheese", 11, "slices", null);

        System.out.println(pantry);

        // Filtering by dietary tag
        System.out.println("Filtered Ingredients (Vegan): ");
        for (Ingredient ingredient : pantry.filter_ingredients_by_tag(Ingredient.dietary_tags.VEGAN)) {
            System.out.println(ingredient);
        }

        // Searching for an ingredient in pantry
        System.out.println("Searching for 'Butter':");
        pantry.searchIngredient("Butter");

        // Searching for ingredients with 'cheese' in the name
        System.out.println("Searching for 'cheese':");
        pantry.searchIngredient("cheese");

        // Searching for 'milk' (should return nothing)
        System.out.println("Searching for 'milk':");
        pantry.searchIngredient("milk");

        // Adding ingredient to the grocery list for refill (from pantry)
        pantry.addToGroceryList("White Bread", 10);

        // Print the grocery list
        pantry.printGroceryList();

        System.out.println("\n");
        //______________________________________________________________________________
        Recipe.createTestGrilledCheeseRecipe().printRecipeDetails();



    }
}
