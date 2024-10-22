import java.util.HashMap;
import java.util.Map;

public class Pantry {
    Map<String, Ingredient> ingredientList = new HashMap<>();

    public void add_ingredient(String name){

        ingredientList.put(name, new Ingredient(name,0, "")); //In the real application this would
                                                                            // be pulling from a pre-set ingredient
                                                                            //database that we made
        Ingredient ing = ingredientList.get(name);
        System.out.println("Added " + ing.name + " to pantry.");
    }

    public void delete_ingredient(String name){
        Ingredient ing = ingredientList.get(name);
        ingredientList.remove(name, ingredientList.get(name));
        System.out.println("Deleted " + ing.name + " from pantry.");
    }

    public void edit_ingredient(String name, int quantity){
        Ingredient ing = ingredientList.get(name);
        ing.quantity = quantity;
        System.out.println("You now have " + ing.quantity + ing.unit + " of " + ing.name + " in pantry.");
    }

    @Override
    public String toString() {
        StringBuilder pantryContents = new StringBuilder("Pantry contents:\n");
        for (Map.Entry<String, Ingredient> entry : ingredientList.entrySet()) {
            Ingredient ing = entry.getValue();
            pantryContents.append(ing.name).append(": ").append(ing.quantity).append(ing.unit).append("\n");
        }
        return pantryContents.toString();
    }

}
