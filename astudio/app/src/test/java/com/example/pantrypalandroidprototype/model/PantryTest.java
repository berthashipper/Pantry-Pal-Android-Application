package com.example.pantrypalandroidprototype.model;

import junit.framework.TestCase;
import org.junit.Test;
import java.util.*;
/**
 * Unit tests for the {@link Pantry} class, verifying its functionality for managing ingredients and grocery lists.
 */
public class PantryTest extends TestCase {

    /**
     * Tests adding an ingredient object to the pantry.
     * Verifies that the ingredient is successfully added to the ingredient list.
     */
    @Test
    public void testAdd_ingredient() {
        // Arrange
        Pantry pantry = new Pantry();
        Ingredient butter = new Ingredient("Butter", 1, "tablespoons", Set.of("VEGETARIAN"));

        // Act
        pantry.add_ingredient(butter);

        // Assert
        assertTrue(pantry.ingredientList.containsKey("butter"));
        assertEquals(butter, pantry.ingredientList.get("butter"));
    }

    /**
     * Tests adding an ingredient to the pantry by specifying its properties directly.
     * Verifies that the ingredient is successfully created and added to the pantry.
     */
    @Test
    public void testTestAdd_ingredient() {
        // Arrange
        Pantry pantry = new Pantry();

        // Act
        pantry.add_ingredient("Milk", 1, "liters", Set.of(Ingredient.dietary_tags.VEGETARIAN));

        // Assert
        assertTrue(pantry.ingredientList.containsKey("milk"));
        Ingredient milk = pantry.ingredientList.get("milk");
        assertEquals("Milk", milk.getName());
        assertEquals(1.0, milk.getQuantity(), 0.001);
        assertEquals("liters", milk.getUnit());
        assertTrue(milk.getTags().contains("VEGETARIAN"));
    }

    /**
     * Tests deleting an ingredient from the pantry by name.
     * Verifies that the ingredient is removed from the ingredient list.
     */
    @Test
    public void testDelete_ingredient() {
        // Arrange
        Pantry pantry = new Pantry();
        Ingredient sugar = new Ingredient("Sugar", 2, "cups", Set.of("VEGETARIAN"));
        pantry.add_ingredient(sugar);

        // Act
        pantry.delete_ingredient("Sugar");

        // Assert
        assertFalse(pantry.ingredientList.containsKey("sugar"));
    }

    /**
     * Tests editing the quantity of an ingredient in the pantry.
     * Verifies that the quantity of the ingredient is updated correctly.
     */
    @Test
    public void testEdit_ingredient() {
        // Arrange
        Pantry pantry = new Pantry();
        Ingredient salt = new Ingredient("Salt", 1, "teaspoons", Set.of("VEGAN"));
        pantry.add_ingredient(salt);

        // Act
        pantry.edit_ingredient("Salt", 2);

        // Assert
        assertEquals(2.0, pantry.ingredientList.get("salt").getQuantity(), 0.001);
    }

    /**
     * Tests filtering ingredients in the pantry by a dietary tag.
     * Verifies that only ingredients with the specified tag are returned.
     */
    @Test
    public void testFilter_ingredients_by_tag() {
        // Arrange
        Pantry pantry = new Pantry();
        pantry.add_ingredient("Flour", 2, "cups", Set.of(Ingredient.dietary_tags.VEGETARIAN));
        pantry.add_ingredient("Eggs", 6, "pieces", Set.of(Ingredient.dietary_tags.NUT_FREE));

        // Act
        List<Ingredient> vegetarianIngredients = pantry.filter_ingredients_by_tag(Ingredient.dietary_tags.VEGETARIAN);

        // Assert
        assertEquals(1, vegetarianIngredients.size());
        assertEquals("Flour", vegetarianIngredients.get(0).getName());
    }

    /**
     * Tests printing ingredients in the pantry filtered by a dietary tag.
     * Verifies the correct output is displayed for the specified tag.
     */
    @Test
    public void testPrintIngredientsByTag() {
        // Arrange
        Pantry pantry = new Pantry();
        pantry.add_ingredient("Cheese", 1, "block", Set.of(Ingredient.dietary_tags.VEGETARIAN));

        // Act & Assert
        pantry.printIngredientsByTag(Ingredient.dietary_tags.VEGETARIAN);
    }

    /**
     * Tests searching for ingredients in the pantry by name.
     * Verifies that matching ingredients are found and displayed.
     */
    @Test
    public void testSearchIngredient() {
        // Arrange
        Pantry pantry = new Pantry();
        pantry.add_ingredient("Tomato", 3, "pieces", Set.of(Ingredient.dietary_tags.VEGAN));

        // Act & Assert
        pantry.searchIngredient("Tomato");
    }

    /**
     * Tests adding an ingredient to the grocery list.
     * Verifies that the ingredient is successfully added with the correct quantity.
     */
    @Test
    public void testAddToGroceryList() {
        // Arrange
        Pantry pantry = new Pantry();
        pantry.add_ingredient("Butter", 1, "tablespoons", Set.of(Ingredient.dietary_tags.VEGETARIAN));

        // Act
        pantry.addToGroceryList("Butter", 2);

        // Assert
        assertTrue(pantry.groceryList.containsKey(pantry.ingredientList.get("butter")));
        assertEquals(2.0, pantry.groceryList.get(pantry.ingredientList.get("butter")), 0.001);
    }

    /**
     * Tests printing the grocery list.
     * Verifies that all items in the grocery list are displayed correctly.
     */
    @Test
    public void testPrintGroceryList() {
        // Arrange
        Pantry pantry = new Pantry();
        pantry.add_ingredient("Milk", 1, "liters", Set.of(Ingredient.dietary_tags.VEGETARIAN));
        pantry.addToGroceryList("Milk", 2);

        // Act & Assert
        pantry.printGroceryList();
    }
}
