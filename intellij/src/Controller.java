import java.util.HashSet;
import java.util.Set;

public class Controller {
    Pantry userPantry;
    Set<Ingredient> ingredientList;
    Recipe allRecipes;


    public Controller(Pantry userPantry){
        this.userPantry = userPantry;
    }

    public void add_ingredient(String name, int quantity, String unit, Set<Ingredient.dietary_tags> tags) {
        userPantry.add_ingredient(name, quantity, unit, tags);
    }

    public void delete_ingredient(String name) {
        userPantry.delete_ingredient(name);
    }

    public void viewPantry(){
        userPantry.toString();
    }

}
