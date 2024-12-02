package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an ingredient used in recipes. An ingredient has a name, quantity, unit, and a set of dietary tags.
 * Provides methods for managing the ingredient's quantity, tags, and printing its details.
 * Implements {@link Serializable} for object serialization.
 */
public class Ingredient implements Serializable {

    /** The name of the ingredient. */
    public final String name;

    /** The quantity of the ingredient. */
    public double quantity;

    /** The unit of measurement for the ingredient. */
    public final String unit;

    /** A set of dietary tags associated with the ingredient. */
    public final Set<String> tags;

    /**
     * Enum representing common dietary tags for ingredients.
     */
    public enum dietary_tags {
        VEGAN,
        KOSHER,
        GLUTEN_FREE,
        HALAL,
        NUT_FREE,
        VEGETARIAN,
        DAIRY_FREE
    }

    /**
     * Constructs an ingredient with specified name, quantity, unit, and dietary tags.
     *
     * @param name   The name of the ingredient.
     * @param quantity The quantity of the ingredient.
     * @param unit     The unit of measurement for the ingredient.
     * @param tags     The set of dietary tags associated with the ingredient.
     */
    public Ingredient(String name, double quantity, String unit, Set<String> tags) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.tags = (tags == null) ? new HashSet<>() : new HashSet<>(tags);
    }

    /**
     * Constructs an ingredient with the specified name and default values for quantity, unit, and tags.
     *
     * @param name The name of the ingredient.
     */
    public Ingredient(String name) {
        this(name, 0.0, "unit", new HashSet<>());  // Default values for quantity and unit
    }

    /**
     * Updates the quantity of the ingredient.
     *
     * @param newQuantity The new quantity of the ingredient.
     */
    public void updateQuantity(double newQuantity) {
        this.quantity = newQuantity;
    }

    /**
     * Returns the name of the ingredient.
     *
     * @return The name of the ingredient.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the quantity of the ingredient.
     *
     * @return The quantity of the ingredient.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Returns the scaled quantity of the ingredient.
     *
     * @return The scaled quantity of the ingredient.
     */
    public void scaleQuantity(double factor) {
        this.quantity *= factor;
    }

    /**
     * Returns the unit of measurement for the ingredient.
     *
     * @return The unit of measurement for the ingredient.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Returns a copy of the tags associated with the ingredient.
     *
     * @return A set of dietary tags associated with the ingredient.
     */
    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Adds a dietary tag to the ingredient.
     *
     * @param tag The dietary tag to add.
     */
    public void addDietaryTag(String tag) {
        tags.add(tag);
    }

    /**
     * Removes a dietary tag from the ingredient.
     *
     * @param tag The dietary tag to remove.
     */
    public void removeDietaryTag(String tag) {
        tags.remove(tag);
    }

    /**
     * Returns a string representation of the ingredient, including its name, quantity, unit, and dietary tags.
     *
     * @return A string representation of the ingredient.
     */
    @Override
    public String toString() {
        StringBuilder ingredientDetails = new StringBuilder();
        ingredientDetails.append("• ").append(name).append("\n")
                .append("   Quantity: ").append(quantity).append(" ").append(unit).append("\n")
                .append("   Tags: ").append(tags.isEmpty() ? "None" : String.join(", ", tags)).append("\n");
        return ingredientDetails.toString();
    }
}
