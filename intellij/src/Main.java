import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Pantry pantry = new Pantry();

        // Creating ingredient objects
        Ingredient bread = new Ingredient("White Bread", 12, "slices", EnumSet.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.VEGETARIAN));
        Ingredient butter = new Ingredient("Butter", 10, "tablespoons", EnumSet.of(Ingredient.dietary_tags.VEGETARIAN, Ingredient.dietary_tags.NUT_FREE));
        Ingredient cheese = new Ingredient("Cheddar Cheese", 11, "slice", EnumSet.of(Ingredient.dietary_tags.VEGETARIAN, Ingredient.dietary_tags.NUT_FREE));

        // Adding ingredients to pantry
        pantry.add_ingredient(butter);
        pantry.add_ingredient(bread);
        pantry.add_ingredient(cheese);
        pantry.add_ingredient("Mozzarella Cheese", 1, "pack", EnumSet.of(Ingredient.dietary_tags.VEGETARIAN, Ingredient.dietary_tags.NUT_FREE));
        pantry.add_ingredient("Apple", 3, "apples", EnumSet.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.VEGETARIAN, Ingredient.dietary_tags.NUT_FREE));

        // Deleting ingredients to pantry
        pantry.delete_ingredient("Apple");

        System.out.println("\n");
        //______________________________________________________________________

        // Display full pantry
        System.out.println(pantry);

        // Filtering by dietary tag
        System.out.println("Nut-free Ingredients in Pantry:");
        for (Ingredient ingredient : pantry.filter_ingredients_by_tag(Ingredient.dietary_tags.NUT_FREE)) {
            System.out.println(ingredient);
        }

        System.out.println("\n");
        //______________________________________________________________________

        // Searching for an ingredient in pantry
        System.out.println("Searching for 'Butter':");
        pantry.searchIngredient("Butter");

        System.out.println("\n");
        // Searching for ingredients with 'cheese' in the name
        System.out.println("Searching for 'cheese':");
        pantry.searchIngredient("cheese");

        System.out.println("\n");
        // Searching for 'milk' (should return nothing)
        System.out.println("Searching for 'milk':");
        pantry.searchIngredient("milk");

        System.out.println("\n");
        //______________________________________________________________________

        // Adding ingredient to the grocery list for refill (from pantry)
        pantry.addToGroceryList("White Bread", 10);

        System.out.println("\n");
        // Print the grocery list
        pantry.printGroceryList();

        System.out.println("\n");
        //______________________________________________________________________

        // Use the RecipeBuilder to create a test recipe
        Recipe grilledCheese = new RecipeBuilder()
                .setName("Grilled Cheese Sandwich")
                .addIngredient(bread)
                .addIngredient(butter)
                .addIngredient(cheese)
                .addInstruction("Heat a skillet over medium heat.")
                .addInstruction("Butter 2 slices of bread and place 1 slice in the skillet, butter side down.")
                .addInstruction("Add 1 slice of cheddar cheese, then top with the second slice of bread, butter side up.")
                .addInstruction("Cook until golden brown and flip to cook the other side.")
                .addTag(Ingredient.dietary_tags.VEGETARIAN)
                .setDescription("A classic grilled cheese sandwich with crispy golden bread and melted cheddar cheese.")
                .setCookTime(LocalTime.of(0, 10))
                .setServingSize(1)
                .build();

        // Display the recipe
        grilledCheese.printRecipeDetails();
    }
}
