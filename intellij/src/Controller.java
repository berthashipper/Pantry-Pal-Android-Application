import java.time.Duration;
import java.util.*;

public class Controller {
    private Pantry pantry;
    private Set<Recipe> allRecipes = new HashSet<>();
    private Cookbook cookbook;

    public Controller(Pantry pantry, Set<Recipe> allRecipes, Cookbook cookbook) {
        this.pantry = pantry;
        this.allRecipes = allRecipes;;
        this.cookbook = cookbook;
    }


    public void addIngredient(String name, int quantity, String unit, Set<Ingredient.dietary_tags> tags) {
        pantry.add_ingredient(name, quantity, unit, tags);
    }

    public void editIngredient(String name, int newQuantity) {
        pantry.edit_ingredient(name, newQuantity);
    }

    public void deleteIngredient(String name) {
        pantry.delete_ingredient(name);
    }

    public String viewPantryContents() {
        return pantry.toString();
    }

    public void generateRecipeSuggestions() {
        Generate_Recipe recipeGenerator = new Generate_Recipe(pantry, allRecipes);
        recipeGenerator.generateMatchingRecipes();
    }

    public void searchIngredientByName(String name) {
        pantry.searchIngredient(name);
    }

    public void printIngredientsByTag(Ingredient.dietary_tags tag) {
        pantry.printIngredientsByTag(tag);
    }

    public void uploadRecipe(String name, String description, Duration cookTime, int servingSize,
                             Set<Ingredient> ingredients, Set<String> instructions) {
        RecipeBuilder recipeBuilder = new RecipeBuilder()
                .setName(name)
                .setDescription(description)
                .setCookTime(cookTime)
                .setServingSize(servingSize);

        // Add each ingredient to the recipe
        for (Ingredient ingredient : ingredients) {
            recipeBuilder.addIngredient(ingredient);
        }

        // Add each instruction to the recipe
        for (String instruction : instructions) {
            recipeBuilder.addInstruction(instruction);
        }

        Recipe newRecipe = recipeBuilder.build();
        allRecipes.add(newRecipe);
    }

    public String viewCookbook() {
        if (allRecipes.isEmpty()) {
            return "Your cookbook is empty.";
        } else {
            StringBuilder result = new StringBuilder();
            result.append("\n______________________________________________________________________\n")
                    .append("--- Your Cookbook ---\n");
            for (Recipe recipe : allRecipes) {
                result.append(recipe.recipeName).append("\n");
            }
            result.append("\n______________________________________________________________________\n");
            return result.toString();
        }
    }

    public String searchRecipeByName(String name) {
        List<Recipe> foundRecipes = new ArrayList<>();
        StringBuilder result = new StringBuilder();

        for (Recipe recipe : allRecipes) {
            if (recipe.recipeName.toLowerCase().contains(name.toLowerCase())) {
                foundRecipes.add(recipe);
                result.append("\n______________________________________________________________________\n");
                result.append(recipe.toString());
            }
        }

        if (foundRecipes.isEmpty()) {
            return "Recipe " + name + " not found in the list.";
        } else {
            return result.toString();
        }
    }


    /* Drafting for next iteration: using the Cookbook class
    public void viewCookbook() {
        if (cookbook.savedRecipes.isEmpty()) {
            System.out.println("Your cookbook is empty.");
        } else {
            System.out.println("--- Your Cookbook ---");
            for (Recipe recipe : cookbook.savedRecipes) {
                System.out.println(recipe.recipeName);
            }
        }
    }

    public void clearCookbook() {
        cookbook.deleteAllSavedRecipes();
        System.out.println("Your Cookbook is cleared.");
    }

    public void saveRecipetoCookbook(Recipe recipe) {
        cookbook.addSavedRecipe(recipe);
    }

    public void deleteRecipefromCookbook(Recipe recipe) {
        cookbook.deleteSavedRecipe(recipe);
    }
     */


}
