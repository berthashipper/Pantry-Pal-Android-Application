import java.time.Duration;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Controller {
    private Pantry pantry;
    private Set<Recipe> allRecipes = new HashSet<>();


    public Controller(Pantry pantry, Set<Recipe> allRecipes) {
        this.pantry = pantry;
        this.allRecipes = allRecipes;;
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

    public void viewPantryContents() {
        System.out.println(pantry.toString());
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
        System.out.println("Recipe uploaded successfully!");
    }

    public void viewCookbook() {
        if (allRecipes.isEmpty()) {
            System.out.println("Your cookbook is empty.");
        } else {
            System.out.println("--- Your Cookbook ---");
            for (Recipe recipe : allRecipes) {
                System.out.println(recipe.recipeName);
            }
        }
    }
}
