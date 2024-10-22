public class Ingredient {
    String name;
    int quantity;

    enum dietary_tags {
        vegan,
        kosher
    }

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void print_ingredient() {
        System.out.println(name + "; " + quantity);
    }
}
