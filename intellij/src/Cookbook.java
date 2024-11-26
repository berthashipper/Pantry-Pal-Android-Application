import java.util.Set;
import java.util.Set;

/**
 * Represents a cookbook that contains a collection of saved recipes.
 */
public class Cookbook {
    /** A set of recipes saved in the cookbook. */
    Set<Recipe> savedRecipes;

    /**
     * Constructs a {@code Cookbook} with the specified set of recipes.
     *
     * @param savedRecipes A set of {@code Recipe} objects to initialize the cookbook.
     */
    public Cookbook(Set<Recipe> savedRecipes) {
        this.savedRecipes = savedRecipes;
    }

    /**
     * Retrieves the set of saved recipes in the cookbook.
     *
     * @return A set containing the saved recipes.
     */
    public Set<Recipe> getSavedRecipes() {
        return savedRecipes;
    }

    /**
     * Deletes all recipes from the cookbook.
     */
    public void deleteAllSavedRecipes() {
        savedRecipes.clear();
    }

    /**
     * Deletes a specific recipe from the cookbook.
     *
     * @param recipe The {@code Recipe} object to remove from the cookbook.
     */
    public void deleteSavedRecipe(Recipe recipe) {
        savedRecipes.remove(recipe);
    }

    /**
     * Adds a recipe to the cookbook.
     *
     * @param recipe The {@code Recipe} object to add to the cookbook.
     */
    public void addSavedRecipe(Recipe recipe) {
        savedRecipes.add(recipe);
    }

    /**
     * Generates a string representation of the cookbook's contents, listing all saved recipe names.
     *
     * @return A formatted string containing the names of all saved recipes.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Recipe r : savedRecipes) {
            sb.append(r.recipeName).append("\n");
        }
        return sb.toString();
    }
}
