import java.util.EnumSet;
import java.util.Scanner;


public class UI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pantry userPantry = new Pantry();
        Controller controller = new Controller(userPantry);

        // Pre-loaded ingredients in user pantry for purpose of prototype UI
        Ingredient bread = new Ingredient("White Bread", 2, "slices", EnumSet.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.VEGETARIAN));
        Ingredient butter = new Ingredient("Butter", 1, "tablespoons", EnumSet.of(Ingredient.dietary_tags.VEGETARIAN, Ingredient.dietary_tags.NUT_FREE));
        Ingredient cheese = new Ingredient("Cheddar Cheese", 1, "slice", EnumSet.of(Ingredient.dietary_tags.VEGETARIAN, Ingredient.dietary_tags.NUT_FREE));

        userPantry.add_ingredient(butter);
        userPantry.add_ingredient(bread);
        userPantry.add_ingredient(cheese);
        userPantry.add_ingredient("Mozzarella Cheese",1,"pack",EnumSet.of(Ingredient.dietary_tags.VEGETARIAN,Ingredient.dietary_tags.NUT_FREE));
        userPantry.add_ingredient("Apple",3,"apples",EnumSet.of(Ingredient.dietary_tags.VEGAN,Ingredient.dietary_tags.VEGETARIAN,Ingredient.dietary_tags.NUT_FREE));

        System.out.println("(1) Manage Pantry or (2) Generate Recipe Suggestions?");
        if (scanner.nextLine().equals("1")){

            System.out.println("Select Action:\n" +
                    "(1) Add ingredients to pantry\n" +
                    "(2) Edit ingredients in pantry\n" +
                    "(3) Delete ingredients from pantry\n");
            if (scanner.nextLine().equals("1")) {
                System.out.println("Enter name of ingredient:");
                String name = scanner.nextLine();
                System.out.println("Enter quantity of ingredient:");
                String quantity = scanner.nextLine();
                System.out.println("Enter unit of ingredient:");
                String unit = scanner.nextLine();
                System.out.println("List of dietary tags. Select applicable ones.\n" +
                        "VEGAN,\n" +
                        "KOSHER,\n" +
                        "GLUTEN_FREE,\n" +
                        "HALAL,\n" +
                        "NUT_FREE,\n" +
                        "VEGETARIAN,\n" +
                        "DAIRY_FREE");
                String tags = scanner.nextLine();
                controller.add_ingredient(name, quantity, unit, tags);
                controller.viewPantry();

            } else if (scanner.nextLine().equals("2")) {

            } else if (scanner.nextLine().equals("3")) {

            }
        } else if (scanner.nextLine().equals("2")){

        } else {

        }

    }

}
