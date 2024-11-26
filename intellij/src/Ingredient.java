import java.util.Set;

/**
 * Represents an ingredient with its name, quantity, unit of measurement, and dietary tags.
 */
public class Ingredient {
    /** The name of the ingredient. */
    String name;

    /** The quantity of the ingredient. */
    double quantity;

    /** The unit of measurement for the quantity (e.g., grams, liters). */
    String unit;

    /** A set of dietary tags associated with the ingredient. */
    Set<dietary_tags> tags;

    /**
     * Enum representing various dietary tags that can be associated with an ingredient.
     */
    public enum dietary_tags {
        /** Indicates the ingredient is suitable for a vegan diet. */
        VEGAN,

        /** Indicates the ingredient is kosher. */
        KOSHER,

        /** Indicates the ingredient is gluten-free. */
        GLUTEN_FREE,

        /** Indicates the ingredient is halal. */
        HALAL,

        /** Indicates the ingredient is free from nuts. */
        NUT_FREE,

        /** Indicates the ingredient is suitable for a vegetarian diet. */
        VEGETARIAN,

        /** Indicates the ingredient is dairy-free. */
        DAIRY_FREE
    }

    /**
     * Constructs an {@code Ingredient} with the specified parameters.
     *
     * @param name     The name of the ingredient.
     * @param quantity The quantity of the ingredient.
     * @param unit     The unit of measurement for the quantity.
     * @param tags     A set of dietary tags associated with the ingredient.
     */
    public Ingredient(String name, double quantity, String unit, Set<dietary_tags> tags) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.tags = tags;
    }

    /**
     * Updates the quantity of the ingredient.
     *
     * @param newQuantity The new quantity to set.
     */
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    /**
     * Retrieves the name of the ingredient.
     *
     * @return The name of the ingredient.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the quantity of the ingredient.
     *
     * @return The quantity of the ingredient.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Retrieves the unit of measurement for the ingredient's quantity.
     *
     * @return The unit of measurement.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Generates a string representation of the ingredient, including its name, quantity, unit, and dietary tags.
     *
     * @return A formatted string containing the ingredient's details.
     */
    @Override
    public String toString() {
        return "Ingredient: " + name + ", Quantity: " + quantity + " " + unit + ", Tags: " + tags;
    }
}
