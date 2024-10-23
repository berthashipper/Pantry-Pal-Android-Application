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
            Set<Ingredient> ingredientsInRecipe = recipe.ingredientsInRecipe;//the ingredients in each recipe
            for (Ingredient ingredient : ingredientsInRecipe) {
                recipeIngredients.add(ingredient.name); //get names of each ingredient in the current recipe
            }
            if (ingredientsInPantry.containsAll(recipeIngredients)) { //checks if the user has all the ingredients
                for (String ingredientr :  recipeIngredients) {
                    Ingredient count = pantry.ingredientList.get(ingredientr); //get the quantity of the ingredient in the pantry
                    for (Ingredient ingredient : ingredientsInRecipe) {
                        if (count.quantity >= ingredient.quantity) { //compare the quantity between the recipe and pantry
                            matchedRecipe.add(recipe); //if it is higher, then add the recipe to the matched list
                        }
                    }
                }
            }
        }
        return matchedRecipe;
    }
    

}
