public class Main{

    public static void main(String[] args) {
        Ingredient banana = new Ingredient("banana", 3, "unit");
        Ingredient salt = new Ingredient("salt", 100, "grams");

        System.out.println(banana);

        Pantry pantry = new Pantry();
        pantry.add_ingredient("apple");
        pantry.delete_ingredient("apple");

        pantry.add_ingredient("banana");
        pantry.edit_ingredient("banana", 4);
        pantry.edit_ingredient("banana", 9);

        pantry.add_ingredient("salt");
        pantry.edit_ingredient("salt", 200);
        pantry.add_ingredient("sugar");

        System.out.println(pantry);
    }
}