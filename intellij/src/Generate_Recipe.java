import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Generates and prints recipes that can be made using ingredients available in the user's pantry.
 */
public class Generate_Recipe {
    /** The user's pantry containing available ingredients. */
    Pantry userPantry;

    /** A set of all available recipes to compare against the pantry ingredients. */
    Set<Recipe> allRecipes;

    /**
     * Constructs a {@code Generate_Recipe} instance with the specified pantry and set of recipes.
     *
     * @param userPantry The user's pantry containing ingredients.
     * @param allRecipes A set of all recipes to consider for matching.
     */
    public Generate_Recipe(Pantry userPantry, Set<Recipe> allRecipes) {
        this.userPantry = userPantry;
        this.allRecipes = allRecipes;
    }

    /**
     * Generates and prints recipes that can be made using the ingredients available in the pantry.
     */
    public void generateMatchingRecipes() {
        Set<Recipe> matchedRecipes = new HashSet<>();

        // Check each recipe to see if it can be made with the pantry ingredients
        for (Recipe recipe : allRecipes) {
            if (canMakeRecipe(recipe)) {
                matchedRecipes.add(recipe);
            }
        }

        // Print the matched recipes
        printMatchedRecipes(matchedRecipes);
    }

    /**
     * Prints the matched recipes, if any, with a numbered list.
     *
     * @param matchedRecipes A set of recipes that can be made with the pantry ingredients.
     */
    private void printMatchedRecipes(Set<Recipe> matchedRecipes) {
        if (matchedRecipes.isEmpty()) {
            System.out.println("No matching recipes found.");
        } else {
            int index = 1;
            System.out.println("Matched Recipes to your Pantry:");
            for (Recipe recipe : matchedRecipes) {
                System.out.println(index + ". " + recipe.recipeName);
                index++;
            }
        }
    }

    /**
     * Checks whether the specified recipe can be made with the ingredients available in the pantry.
     *
     * @param recipe The {@code Recipe} object to check.
     * @return {@code true} if the pantry contains all required ingredients in sufficient quantity;
     *         {@code false} otherwise.
     */
    private boolean canMakeRecipe(Recipe recipe) {
        // Check each ingredient in the recipe
        for (Ingredient recipeIngredient : recipe.getIngredients()) {
            // Find the ingredient in the pantry
            Ingredient pantryIngredient = userPantry.ingredientList.get(recipeIngredient.name.toLowerCase());

            // Verify the pantry has the ingredient and the required quantity
            if (pantryIngredient == null || pantryIngredient.quantity < recipeIngredient.quantity) {
                return false; // Missing ingredient or insufficient quantity
            }
        }
        return true; // All ingredients are available in sufficient quantity
    }


    /* Drafting for next iteration
    public void generateGroceryList (Recipe recipe) {
        Map<String, Ingredient> pantryIngredients = userPantry.ingredientList; //ingredients in pantry

            if (!canMakeRecipe(recipe)) {
                Set<Ingredient> inRecipe = recipe.ingredientsInRecipe;
                for (Ingredient ingredient : inRecipe) {
                    Ingredient pantryIngredient = pantryIngredients.get(ingredient.name.toLowerCase());
                    if (pantryIngredient == null) {
                        userPantry.addToGroceryList(ingredient.name.toLowerCase(), ingredient.quantity);
                    } else if(pantryIngredient.quantity < ingredient.quantity) {
                            double count = ingredient.quantity - pantryIngredient.quantity;
                            userPantry.addToGroceryList(ingredient.name.toLowerCase(), count);
                            System.out.println("Added " + ingredient.name.toLowerCase() + " to grocery list.");
                        }

            }
        }


    }*/
}
