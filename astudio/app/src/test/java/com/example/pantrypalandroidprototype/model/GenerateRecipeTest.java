package com.example.pantrypalandroidprototype.model;
import junit.framework.TestCase;

import org.junit.Test;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for the {@link GenerateRecipe} class, verifying its functionality for managing pantry.
 */
public class GenerateRecipeTest extends TestCase {

    /**
     * Tests the {@link GenerateRecipe#generateMatchingRecipes} method to ensure it correctly identifies
     * recipes that can be made from the available pantry ingredients. This test verifies that the method
     * returns the correct recipe when the pantry has all the required ingredients with enough quantities.
     *
     * Steps:
     * - Create a set of ingredients for a simple recipe.
     * - Add the ingredients to the pantry.
     * - Create a recipe and add it to the set of all recipes.
     * - Generate matching recipes and assert that the recipe is correctly identified.
     */
    @Test
    public void testGenerateMatchingRecipes() {
        // Create Ingredients
        Ingredient flour = new Ingredient("flour", 2.0, "cups", new HashSet<>());
        Ingredient sugar = new Ingredient("sugar", 1.5, "cups", new HashSet<>());
        Ingredient eggs = new Ingredient("eggs", 3, "pieces", new HashSet<>());

        // Create a Recipe with these ingredients
        Set<Ingredient> cakeIngredients = new HashSet<>();
        cakeIngredients.add(flour);
        cakeIngredients.add(sugar);
        cakeIngredients.add(eggs);
        Recipe cakeRecipe = new Recipe("Cake", cakeIngredients, "Mix all ingredients and bake.", new HashSet<>(),
                "A simple cake recipe", Duration.ofMinutes(30), 4, "www.example.com");

        // Create Pantry with ingredients
        Pantry pantry = new Pantry();
        pantry.add_ingredient(flour);
        pantry.add_ingredient(sugar);
        pantry.add_ingredient(eggs);

        // Add Recipe to the set of all recipes
        Set<Recipe> allRecipes = new HashSet<>();
        allRecipes.add(cakeRecipe);

        // Create GenerateRecipe object with the pantry and recipes
        GenerateRecipe generateRecipe = new GenerateRecipe(pantry, allRecipes);

        // Call the method to generate matching recipes
        Set<Recipe> matchedRecipes = generateRecipe.generateMatchingRecipes();

        // Assert that the recipe is matched
        assertTrue("The recipe should be in the matched recipes.", matchedRecipes.contains(cakeRecipe));
    }

    /**
     * Tests the {@link GenerateRecipe#canMakeRecipe} method to ensure it correctly checks whether a recipe
     * can be made with the ingredients available in the pantry. It verifies that the method returns true
     * when the pantry contains all the necessary ingredients with sufficient quantities.
     *
     * Steps:
     * - Create a set of ingredients for a simple recipe.
     * - Add the ingredients to the pantry.
     * - Create the recipe and call the canMakeRecipe method to verify if the recipe can be made.
     * - Assert that the method returns true.
     */
    @Test
    public void testCanMakeRecipe() {
        // Create Ingredients
        Ingredient flour = new Ingredient("flour", 2.0, "cups", new HashSet<>());
        Ingredient sugar = new Ingredient("sugar", 1.5, "cups", new HashSet<>());
        Ingredient eggs = new Ingredient("eggs", 3, "pieces", new HashSet<>());

        // Create a Recipe with these ingredients
        Set<Ingredient> cakeIngredients = new HashSet<>();
        cakeIngredients.add(flour);
        cakeIngredients.add(sugar);
        cakeIngredients.add(eggs);
        Recipe cakeRecipe = new Recipe("Cake", cakeIngredients, "Mix all ingredients and bake.", new HashSet<>(),
                "A simple cake recipe", Duration.ofMinutes(30), 4, "www.example.com");

        // Create Pantry with ingredients
        Pantry pantry = new Pantry();
        pantry.add_ingredient(flour);
        pantry.add_ingredient(sugar);
        pantry.add_ingredient(eggs);

        // Create GenerateRecipe object
        GenerateRecipe generateRecipe = new GenerateRecipe(pantry, new HashSet<>());

        // Test if the recipe can be made
        boolean canMake = generateRecipe.canMakeRecipe(cakeRecipe);

        // Assert that the recipe can be made
        assertTrue("The recipe should be possible to make with the pantry ingredients.", canMake);
    }

    /**
     * Tests the {@link GenerateRecipe#findMatchingPantryIngredient} method to ensure it correctly identifies
     * matching ingredients in the pantry. This test verifies both exact matches and cases where no match
     * is found.
     *
     * Steps:
     * - Create a set of ingredients and add them to the pantry.
     * - Test if the method correctly finds an exact match for a pantry ingredient.
     * - Test if the method returns null when searching for an ingredient that doesn't exist in the pantry.
     *
     * Asserts:
     * - The method should find the "flour" ingredient.
     * - The method should not find the "butter" ingredient (not present in pantry).
     */
    @Test
    public void testFindMatchingPantryIngredient() {
        // Create Ingredients
        Ingredient flour = new Ingredient("flour", 2.0, "cups", new HashSet<>());
        Ingredient sugar = new Ingredient("sugar", 1.5, "cups", new HashSet<>());
        Ingredient eggs = new Ingredient("eggs", 3, "pieces", new HashSet<>());

        // Create Pantry and add ingredients
        Pantry pantry = new Pantry();
        pantry.add_ingredient(flour);
        pantry.add_ingredient(sugar);
        pantry.add_ingredient(eggs);

        // Create GenerateRecipe object
        GenerateRecipe generateRecipe = new GenerateRecipe(pantry, new HashSet<>());

        // Test finding an exact match
        Ingredient foundIngredient = generateRecipe.findMatchingPantryIngredient("flour");
        assertNotNull("The ingredient 'flour' should be found.", foundIngredient);
        assertEquals("The found ingredient should be 'flour'.", "flour", foundIngredient.getName());

        // Test finding a non-existent ingredient
        Ingredient missingIngredient = generateRecipe.findMatchingPantryIngredient("butter");
        assertNull("The ingredient 'butter' should not be found.", missingIngredient);
    }
}
