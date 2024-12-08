package com.example.pantrypalandroidprototype.model;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Set;

public class CookbookTest extends TestCase {


    @Test
    public void testAddRecipe() {
        // Arrange
        Cookbook cookbook = new Cookbook();
        Recipe newRecipe = new RecipeBuilder()
                .setName("Test Recipe")
                .addIngredient("Test Ingredient", 1, "cup", null)
                .addInstruction("Test instruction.")
                .build();

        // Act
        cookbook.addRecipe(newRecipe);

        // Assert
        assertTrue(cookbook.recipeList.containsKey("Test Recipe"));
        assertEquals(newRecipe, cookbook.recipeList.get("Test Recipe"));
    }

    @Test
    public void testSearchRecipes() {
        // Arrange
        Cookbook cookbook = new Cookbook();
        String searchKeyword = "Salad";

        // Act
        Set<Recipe> foundRecipes = cookbook.searchRecipes(searchKeyword);

        // Assert
        assertNotNull(foundRecipes);
        assertFalse(foundRecipes.isEmpty());
        for (Recipe recipe : foundRecipes) {
            assertTrue(recipe.recipeName.toLowerCase().contains(searchKeyword.toLowerCase()));
        }
    }

    @Test
    public void testRemoveRecipe() {
        // Arrange
        Cookbook cookbook = new Cookbook();
        Recipe recipeToRemove = new RecipeBuilder()
                .setName("Test Recipe")
                .addIngredient("Ingredient", 1, "unit", null)
                .addInstruction("Instruction.")
                .build();
        cookbook.addRecipe(recipeToRemove);

        // Act
        cookbook.removeRecipe(recipeToRemove);

        // Assert
        assertFalse(cookbook.recipeList.containsKey("Test Recipe"));
    }

    @Test
    public void testInitializeDefaultRecipes() {
        // Arrange
        Cookbook cookbook = new Cookbook();

        // Act
        int defaultRecipeCount = cookbook.recipeList.size();

        // Assert
        assertTrue(defaultRecipeCount > 0);
        assertTrue(cookbook.recipeList.containsKey("Spaghetti Bolognese"));
        assertTrue(cookbook.recipeList.containsKey("Caesar Salad"));
    }
}
