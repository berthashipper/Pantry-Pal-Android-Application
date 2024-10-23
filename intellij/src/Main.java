import java.util.EnumSet;

public class Main {
    public static void main(String[] args) {
        Pantry pantry = new Pantry();

        // Adding ingredients with dietary tags
        pantry.add_ingredient("apple", 10, "pieces", EnumSet.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.KOSHER));
        pantry.add_ingredient("banana", 5, "pieces", EnumSet.of(Ingredient.dietary_tags.VEGAN));
        pantry.add_ingredient("bread", 2, "loaves", EnumSet.of(Ingredient.dietary_tags.GLUTEN_FREE, Ingredient.dietary_tags.VEGAN));
        pantry.add_ingredient("mozzarella cheese", 1, "pack",null);
        pantry.add_ingredient("cheddar cheese", 11, "slices", null);

        System.out.println(pantry);

        // Filtering by dietary tag
        System.out.println("Filtered Ingredients (Vegan): ");
        for (Ingredient ingredient : pantry.filter_ingredients_by_tag(Ingredient.dietary_tags.VEGAN)) {
            System.out.println(ingredient);
        }

        // Searching for an ingredient in pantry
        System.out.println("Searching for 'banana':");
        pantry.searchIngredient("banana");

        // Searching for ingredients with 'cheese' in the name
        System.out.println("Searching for 'cheese':");
        pantry.searchIngredient("cheese");

        // Searching for 'milk' (should return nothing)
        System.out.println("Searching for 'milk':");
        pantry.searchIngredient("milk");

        // Adding ingredient to the grocery list for refill (from pantry)
        pantry.addToGroceryList("bread", 4);

        // Print the grocery list
        pantry.printGroceryList();
    }
}
