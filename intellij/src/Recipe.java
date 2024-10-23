import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Recipe {
    String recipeName;
    Set<Ingredient> ingredientsInRecipe;
    String instructions;
    Set<Ingredient.dietary_tags> recipeTags;
    String recipeDescription;
    LocalTime cookTime;
    int servingSize;

    // Constructor method for Recipe
    public Recipe(String recipeName, Set<Ingredient> ingredientsInRecipe,
                  String instructions, Set<Ingredient.dietary_tags> recipeTags,
                  String recipeDescription, LocalTime cookTime, int servingSize){

        this.recipeName = recipeName;
        this.ingredientsInRecipe = ingredientsInRecipe;
        this.instructions = instructions;
        this.recipeTags = recipeTags;
        this.recipeDescription = recipeDescription;
        this.cookTime = cookTime;
        this.servingSize = servingSize;
    }

    // Method to add ingredients to a recipe
    public void addIngredient(Ingredient ingredient) {
        if (ingredientsInRecipe == null) {
            ingredientsInRecipe = new HashSet<>();
        }
        ingredientsInRecipe.add(ingredient);
    }

    // Method to print recipe details
    public void printRecipeDetails() {
        System.out.println("Recipe: " + recipeName);
        System.out.println("Description: " + recipeDescription);
        System.out.println("Cook Time: " + cookTime + " minutes");
        System.out.println("Serves: " + servingSize);
        System.out.println("\nIngredients:");
        for (Ingredient ingredient : ingredientsInRecipe) {
            System.out.println("- " + ingredient.quantity + " " + ingredient.unit + " of " + ingredient.name);
        }
        System.out.println("\nInstructions: " + instructions);
    }

    // Static method to create a default Grilled Cheese recipe
    public static Recipe createTestGrilledCheeseRecipe() {
        // Create a Set of ingredients for the recipe
        Set<Ingredient> grilledCheeseIngredients = new HashSet<>();
        grilledCheeseIngredients.add(new Ingredient("White Bread", 2, "slices", EnumSet.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.VEGETARIAN)));
        grilledCheeseIngredients.add(new Ingredient("Butter", 1, "tablespoon", EnumSet.of(Ingredient.dietary_tags.VEGETARIAN)));
        grilledCheeseIngredients.add(new Ingredient("Cheddar Cheese", 1, "slice", EnumSet.of(Ingredient.dietary_tags.VEGETARIAN)));

        // Recipe dietary tags
        Set<Ingredient.dietary_tags> grilledCheeseTags = EnumSet.of(Ingredient.dietary_tags.VEGETARIAN);

        // Create and return the recipe
        return new Recipe(
                "Grilled Cheese",
                grilledCheeseIngredients,
                "\n1. Heat a skillet over medium heat.\n2. Butter 2 slices of bread and place 1 slice in the skillet, butter side down.\n3. Add 1 slice of cheddar cheese, then top with the second slice of bread, butter side up.\n4. Cook until golden brown and flip to cook the other side.",
                grilledCheeseTags,
                "A classic grilled cheese sandwich with crispy golden bread and melted cheddar cheese.",
                LocalTime.of(0, 10),
                1
        );
    }

}
