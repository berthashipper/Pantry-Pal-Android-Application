import java.util.Set;

public class Cookbook {
    Set<Recipe> savedRecipes;

    public Cookbook(Set<Recipe> savedRecipes) {
        this.savedRecipes = savedRecipes;
    }

    public Set<Recipe> getSavedRecipes() {
        return savedRecipes;
    }

    public void deleteAllSavedRecipes() {
        savedRecipes.clear();
    }

    public void deleteSavedRecipe(Recipe recipe) {
        savedRecipes.remove(recipe);
    }

    public void addSavedRecipe(Recipe recipe) {
        savedRecipes.add(recipe);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Recipe r : savedRecipes){
            sb.append(r.recipeName);
        }
        return sb.toString();
    }

}
