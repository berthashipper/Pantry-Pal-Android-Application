package com.example.pantrypalandroidprototype.model;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * Unit tests for the {@link RecipeBuilder} class, verifying its functionality for building Recipes.
 */

public class RecipeBuilderTest extends TestCase {

    /**
     * Tests the {@link RecipeBuilder#addIngredient(Ingredient)} method to ensure that ingredients
     * are correctly added to the recipe.
     *
     * Asserts:
     * - The ingredient should be present in the recipe's ingredients list.
     */
    @Test
    public void testAddIngredient() {
        RecipeBuilder builder = new RecipeBuilder();
        Ingredient ingredient = new Ingredient("Tomato", 2, "pieces", Set.of("VEGAN", "GLUTEN_FREE"));
        builder.addIngredient(ingredient);

        Recipe recipe = builder.build();
        assertTrue(recipe.getIngredients().contains(ingredient));
    }

    /**
     * Tests the method to ensure that ingredients
     * can be added using a simplified format (ingredient name, quantity, unit, and dietary tags).
     *
     * Asserts:
     * - The recipe should have exactly one ingredient (Potato).
     * - The ingredient should have the correct name, quantity, and unit.
     */
    @Test
    public void testTestAddIngredient() {
        RecipeBuilder builder = new RecipeBuilder();
        builder.addIngredient("Potato", 5, "kg", Set.of("VEGAN", "GLUTEN_FREE"));

        Recipe recipe = builder.build();
        assertEquals(1, recipe.getIngredients().size());
        assertTrue(recipe.getIngredients().stream()
                .anyMatch(i -> i.getName().equals("Potato") && i.getQuantity() == 5 && i.getUnit().equals("kg")));
    }

    /**
     * Tests the {@link RecipeBuilder#addInstruction(String)} method to ensure that multiple instructions
     * can be added to the recipe, and that the instructions are correctly formatted.
     *
     * Asserts:
     * - All instructions should be present in the final recipe.
     * - Instructions should be separated by newlines.
     */
    @Test
    public void testAddInstruction() {
        RecipeBuilder builder = new RecipeBuilder();
        builder.addInstruction("Chop vegetables");
        builder.addInstruction("Boil water");
        builder.addInstruction("Cook for 10 minutes");

        Recipe recipe = builder.build();
        String instructions = recipe.getInstructions();
        assertTrue(instructions.contains("Chop vegetables"));
        assertTrue(instructions.contains("Boil water"));
        assertTrue(instructions.contains("Cook for 10 minutes"));
        assertTrue(instructions.contains("\n")); // Check if instructions are joined by newline
    }

    /**
     * Tests the method to ensure that dietary tags
     * are correctly added to the recipe.
     *
     * Asserts:
     * - The recipe should contain the tags VEGAN and GLUTEN_FREE.
     */
    public void testAddTag() {
        RecipeBuilder builder = new RecipeBuilder();
        builder.addTag("VEGAN");
        builder.addTag("GLUTEN_FREE");

        Recipe recipe = builder.build();
        assertTrue(recipe.getTags().contains("VEGAN"));
        assertTrue(recipe.getTags().contains("GLUTEN_FREE"));
    }

    /**
     * Tests the {@link RecipeBuilder#build()} method to ensure that a fully constructed recipe is correctly built
     * with all attributes, including ingredients, instructions, tags, and other metadata.
     *
     * Asserts:
     * - The recipe should have the correct name, serving size, instructions, tags, cook time, and URL.
     * - All the added ingredients, instructions, and tags should be present.
     */
    @Test
    public void testBuild() {
        RecipeBuilder builder = new RecipeBuilder();
        builder.setName("Vegetable Soup")
                .addIngredient("Carrot", 3, "pieces", null)
                .addInstruction("Chop all vegetables")
                .addInstruction("Cook in boiling water for 20 minutes")
                .addTag("VEGAN")
                .setDescription("A healthy and nutritious soup")
                .setCookTime(Duration.ofMinutes(30))
                .setServingSize(4)
                .setUrl("http://example.com/vegetable-soup");

        Recipe recipe = builder.build();
        assertEquals("Vegetable Soup", recipe.getRecipeName());
        assertEquals(4, recipe.getServingSize());
        assertTrue(recipe.getInstructions().contains("Chop all vegetables"));
        assertTrue(recipe.getTags().contains("VEGAN"));
        assertEquals(Duration.ofMinutes(30), recipe.getCookTime());
        assertEquals("http://example.com/vegetable-soup", recipe.getUrl());
    }
}
