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

    @Test
    public void testAddIngredient() {
        RecipeBuilder builder = new RecipeBuilder();
        Ingredient ingredient = new Ingredient("Tomato", 2, "pieces", Set.of("VEGAN", "GLUTEN_FREE"));
        builder.addIngredient(ingredient);

        Recipe recipe = builder.build();
        assertTrue(recipe.getIngredients().contains(ingredient));
    }

    @Test
    public void testTestAddIngredient() {
        RecipeBuilder builder = new RecipeBuilder();
        builder.addIngredient("Potato", 5, "kg", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.GLUTEN_FREE));

        Recipe recipe = builder.build();
        assertEquals(1, recipe.getIngredients().size());
        assertTrue(recipe.getIngredients().stream()
                .anyMatch(i -> i.getName().equals("Potato") && i.getQuantity() == 5 && i.getUnit().equals("kg")));
    }

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

    public void testAddTag() {
        RecipeBuilder builder = new RecipeBuilder();
        builder.addTag(Ingredient.dietary_tags.VEGAN);
        builder.addTag(Ingredient.dietary_tags.GLUTEN_FREE);

        Recipe recipe = builder.build();
        assertTrue(recipe.getTags().contains(Ingredient.dietary_tags.VEGAN));
        assertTrue(recipe.getTags().contains(Ingredient.dietary_tags.GLUTEN_FREE));
    }

    @Test
    public void testBuild() {
        RecipeBuilder builder = new RecipeBuilder();
        builder.setName("Vegetable Soup")
                .addIngredient("Carrot", 3, "pieces", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Chop all vegetables")
                .addInstruction("Cook in boiling water for 20 minutes")
                .addTag(Ingredient.dietary_tags.VEGAN)
                .setDescription("A healthy and nutritious soup")
                .setCookTime(Duration.ofMinutes(30))
                .setServingSize(4)
                .setUrl("http://example.com/vegetable-soup");

        Recipe recipe = builder.build();
        assertEquals("Vegetable Soup", recipe.getRecipeName());
        assertEquals(4, recipe.getServingSize());
        assertTrue(recipe.getInstructions().contains("Chop all vegetables"));
        assertTrue(recipe.getTags().contains(Ingredient.dietary_tags.VEGAN));
        assertEquals(Duration.ofMinutes(30), recipe.getCookTime());
        assertEquals("http://example.com/vegetable-soup", recipe.getUrl());
    }
}
