public class Ingredient {
    String name;
    int quantity;
    String unit;

    enum dietary_tags {
        vegan,
        kosher
    }

    public Ingredient(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Ingredient: " + name + ", Quantity: " + quantity + unit;
    }
}
