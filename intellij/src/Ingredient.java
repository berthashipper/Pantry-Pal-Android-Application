import java.util.EnumSet;
import java.util.Set;

public class Ingredient {
    String name;
    int quantity;
    String unit;
    Set<dietary_tags> tags;

    // Enum for dietary tags
    public enum dietary_tags {
        VEGAN,
        KOSHER,
        GLUTEN_FREE,
        HALAL,
        NUT_FREE,
        VEGETARIAN
    }

    // Constructor
    public Ingredient(String name, int quantity, String unit, Set<dietary_tags> tags) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.tags = tags;
    }

    // toString method to view ingredient
    @Override
    public String toString() {
        return "Ingredient: " + name + ", Quantity: " + quantity + " " + unit + ", Tags: " + tags;
    }
}