import java.util.Set;

public class Ingredient {
    String name;
    double quantity;
    String unit;
    Set<dietary_tags> tags;

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

    // Constructor
    public Ingredient(String name, double quantity, String unit, Set<dietary_tags> tags) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.tags = tags;
    }

    public void updateQuantity(int newQuantity) {
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

    // toString method to view ingredient
    @Override
    public String toString() {
        return "Ingredient: " + name + ", Quantity: " + quantity + " " + unit + ", Tags: " + tags;
    }
}