package com.example.pantrypalandroidprototype.model;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Set;
/**
 * Unit tests for the {@link Ingredient} class, verifying its functionality for managing pantry.
 */

public class IngredientTest extends TestCase {

    Ingredient test = new Ingredient("Butter", 1, "tablespoons", Set.of("VEGETARIAN", "NUT_FREE"));
    /**
     * Tests updating an ingredient quantity to the ingredient.
     * Verifies that the ingredient is successfully updated.
     */
    @Test
    public void testUpdateQuantity() {
        // Arrange
        double newQuantity = 2.5;

        // Act
        test.updateQuantity(newQuantity);

        // Assert
        assertEquals(newQuantity, test.getQuantity(), 0.001); // Use a small delta for floating-point comparison
    }

    /**
     * Tests adding a new ingredient tag to ingredient.
     * Verifies that the tag is successfully added to the ingredient.
     */
    @Test
    public void testAddDietaryTag() {
        // Arrange
        String newTag = "GLUTEN_FREE";

        // Act
        test.addDietaryTag(newTag);

        // Assert
        assertTrue(test.getTags().contains(newTag));
    }

    /**
     * Tests removing an ingredient tag from ingredient.
     * Verifies that the tag is successfully remove from the ingredient.
     */
    @Test
    public void testRemoveDietaryTag() {
        // Arrange
        String tagToRemove = "NUT_FREE";

        // Act
        test.removeDietaryTag(tagToRemove);

        // Assert
        assertFalse(test.getTags().contains(tagToRemove));
    }
}
