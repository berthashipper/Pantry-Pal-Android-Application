import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.Duration;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A builder class for creating {@link Recipe} objects step-by-step.
 */
public class RecipeBuilder {
    /** The name of the recipe. */
    private String recipeName;

    /** The set of ingredients required for the recipe. */
    private Set<Ingredient> ingredients = new HashSet<>();

    /** The list of instructions for preparing the recipe. */
    private List<String> instructions = new ArrayList<>();

    /** The set of dietary tags associated with the recipe. */
    private Set<Ingredient.dietary_tags> tags = new HashSet<>();

    /** A description of the recipe. */
    private String description;

    /** The cooking time required for the recipe. */
    private Duration cookTime;

    /** The number of servings the recipe makes. */
    private int servingSize;

    /** The URL for additional recipe details or reference. */
    private String url;

    /**
     * Sets the name of the recipe.
     *
     * @param name The recipe name.
     * @return The current {@code RecipeBuilder} instance.
     */
    public RecipeBuilder setName(String name) {
        this.recipeName = name;
        return this;
    }

    /**
     * Adds an {@link Ingredient} object to the recipe.
     *
     * @param ingredient The ingredient to add.
     * @return The current {@code RecipeBuilder} instance.
     */
    public RecipeBuilder addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    /**
     * Adds an ingredient to the recipe using individual attributes.
     *
     * @param name         The name of the ingredient.
     * @param quantity     The quantity of the ingredient.
     * @param unit         The unit of measurement for the ingredient.
     * @param dietaryTags  The dietary tags associated with the ingredient.
     * @return The current {@code RecipeBuilder} instance.
     */
    public RecipeBuilder addIngredient(String name, int quantity, String unit, Set<Ingredient.dietary_tags> dietaryTags) {
        Ingredient ingredient = new Ingredient(name, quantity, unit, dietaryTags);
        ingredients.add(ingredient);
        return this;
    }

    /**
     * Adds a step to the recipe's instructions.
     *
     * @param instruction The instruction to add.
     * @return The current {@code RecipeBuilder} instance.
     */
    public RecipeBuilder addInstruction(String instruction) {
        instructions.add(instruction);
        return this;
    }

    /**
     * Adds a dietary tag to the recipe.
     *
     * @param tag The dietary tag to add.
     * @return The current {@code RecipeBuilder} instance.
     */
    public RecipeBuilder addTag(Ingredient.dietary_tags tag) {
        tags.add(tag);
        return this;
    }

    /**
     * Sets the description of the recipe.
     *
     * @param description The recipe description.
     * @return The current {@code RecipeBuilder} instance.
     */
    public RecipeBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the cooking time for the recipe.
     *
     * @param time The {@link Duration} representing the cook time.
     * @return The current {@code RecipeBuilder} instance.
     */
    public RecipeBuilder setCookTime(Duration time) {
        this.cookTime = time;
        return this;
    }

    /**
     * Sets the serving size of the recipe.
     *
     * @param size The number of servings.
     * @return The current {@code RecipeBuilder} instance.
     */
    public RecipeBuilder setServingSize(int size) {
        this.servingSize = size;
        return this;
    }

    /**
     * Sets the URL associated with the recipe.
     *
     * @param url The URL as a {@code String}.
     * @return The current {@code RecipeBuilder} instance.
     */
    public RecipeBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Builds and returns a {@link Recipe} object based on the accumulated parameters.
     *
     * @return A fully constructed {@code Recipe} object.
     */
    public Recipe build() {
        // Combine instructions into a single string, each step on a new line.
        String joinedInstructions = instructions.stream().collect(Collectors.joining("\n"));

        // Create and return the Recipe object.
        return new Recipe(recipeName, ingredients, joinedInstructions, tags, description, cookTime, servingSize, url);
    }
}
