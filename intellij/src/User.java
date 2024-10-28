public class User {
    private String username;
    private Pantry pantry;

    public User(String username) {
        this.username = username;
        this.pantry = new Pantry();
    }

    // Getters and Setters for username
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    // Getters for Pantry
    public Pantry getPantry() {
        return pantry;
    }

    // Convenience methods to interact with the user's pantry
//    public void viewPantry() {
//        System.out.println(pantry);
//    }
//
//    public void addIngredientToPantry(Ingredient ingredient) {
//        pantry.add_ingredient(ingredient);
//    }
//
//    public void deleteIngredientFromPantry(String name) {
//        pantry.delete_ingredient(name);
//    }
//
//    public void editIngredientQuantity(String name, int newQuantity) {
//        pantry.edit_ingredient(name, newQuantity);
//    }
//
//    public void filterPantryByDietaryTag(Ingredient.dietary_tags tag) {
//        pantry.printIngredientsByTag(tag);
//    }
//
//    public void searchIngredientInPantry(String name) {
//        pantry.searchIngredient(name);
//    }
//
//    public void addToGroceryList(String name, int quantity) {
//        pantry.addToGroceryList(name, quantity);
//    }
//
//    public void viewGroceryList() {
//        pantry.printGroceryList();
//    }
//
}

