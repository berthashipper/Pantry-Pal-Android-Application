import java.util.List;

public class Ingredient {
    String name;
    int quantity;

    enum dietary_tags {
        vegan,
        kosher
    }

    public Ingredient(String name, int quantity){
        name = this.name;
        quantity = this.quantity;
    }

    public String print_ingredient(){
        return name + "; " + quantity;
    }

}

