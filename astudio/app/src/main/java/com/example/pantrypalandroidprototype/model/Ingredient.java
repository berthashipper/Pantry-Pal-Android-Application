package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Ingredient implements Serializable {
    public final String name;
    public double quantity;
    public final String unit;
    public final Set<String> tags;

    // Enum for dietary tags
    public enum dietary_tags {
        VEGAN,
        KOSHER,
        GLUTEN_FREE,
        HALAL,
        NUT_FREE,
        VEGETARIAN,
        DAIRY_FREE
    }

    // Constructor with default tags as empty
    public Ingredient(String name, double quantity, String unit, Set<String> tags) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.tags = (tags == null) ? new HashSet<>() : new HashSet<>(tags);
    }

    // Constructor for name only (default values for quantity, unit, and tags)
    public Ingredient(String name) {
        this(name, 0.0, "unit", new HashSet<>());  // Default values for quantity and unit
    }

    public void updateQuantity(double newQuantity) {
        this.quantity = newQuantity;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    public void addDietaryTag(String tag) {
        tags.add(tag);
    }

    public void removeDietaryTag(String tag) {
        tags.remove(tag);
    }

    // toString method to print ingredient
    @Override
    public String toString() {
        StringBuilder ingredientDetails = new StringBuilder();
        ingredientDetails.append("• ").append(name).append("\n")
                .append("   Quantity: ").append(quantity).append(" ").append(unit).append("\n")
                .append("   Tags: ").append(tags.isEmpty() ? "None" : String.join(", ", tags)).append("\n");
        return ingredientDetails.toString();
    }

}
