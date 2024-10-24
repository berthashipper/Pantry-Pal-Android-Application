import java.util.HashSet;
import java.util.Set;

public class Generate_Recipe {
    Pantry userPantry;
    Set<Recipe> allRecipes;

    // Constructor
    public Generate_Recipe(Pantry userPantry, Set<Recipe> allRecipes) {
        this.userPantry = userPantry;
        this.allRecipes = allRecipes;
    }

    // Main method to generate matching recipes based on pantry ingredients
    public void generateMatchingRecipes() {
        Set<Recipe> matchedRecipes = new HashSet<>();

        for (Recipe recipe : allRecipes) {
            if (canMakeRecipe(recipe)) {
                matchedRecipes.add(recipe);
            }
        }

        // Print matched recipes
        printMatchedRecipes(matchedRecipes);
    }

    // Method to print matched recipes with numbering
    private void printMatchedRecipes(Set<Recipe> matchedRecipes) {
        if (matchedRecipes.isEmpty()) {
            System.out.println("No matching recipes found.");
        } else {
            int index = 1;
            System.out.println("Matched Recipes:");
            for (Recipe recipe : matchedRecipes) {
                System.out.println(index + ". " + recipe.recipeName);
                index++;
            }
        }
    }

    // Helper method to check if the pantry has enough of all ingredients to make the recipe
    private boolean canMakeRecipe(Recipe recipe) {
        for (Ingredient recipeIngredient : recipe.getIngredients()) {
            Ingredient pantryIngredient = userPantry.ingredientList.get(recipeIngredient.name.toLowerCase());

            // Check if the pantry has the ingredient and enough quantity
            if (pantryIngredient == null || pantryIngredient.quantity < recipeIngredient.quantity) {
                return false; // Missing ingredient or not enough quantity
            }
        }
        return true; // All ingredients are available in sufficient quantity
    }
}
