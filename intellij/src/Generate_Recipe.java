import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Generate_Recipe {
    Pantry userPantry;
    Set<Recipe> allRecipes;

    public Generate_Recipe(Pantry userPantry, Set<Recipe> allRecipes) {
        this.userPantry = userPantry;
        this.allRecipes = allRecipes;
    }

    public Set<Recipe> Generator(Pantry pantry, Set<Recipe> recipes) {
        Set<String> ingredientsInPantry = pantry.ingredientList.keySet(); //all the names of ingredients in pantry
        Set<String> recipeIngredients = new HashSet<String>();
        Set<Recipe> matchedRecipe = new HashSet<>();
        for (Recipe recipe : recipes) { //iterating through all the recipe
            Set<Ingredient> ingredientsInRecipe = recipe.ingredientsInRecipe; //the ingredients in each recipe
            for (Ingredient ingredient : ingredientsInRecipe) {
                recipeIngredients.add(ingredient.name); //get names of each ingredient in the current recipe
            }
            if (ingredientsInPantry.containsAll(recipeIngredients)) {
                matchedRecipe.add(recipe);
            }

        }
        return matchedRecipe;
    }
    

}
