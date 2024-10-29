import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class UI {
    public static <Int> void main(String[] args) {
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
                int quantity = scanner.nextInt();
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
                Set<Ingredient.dietary_tags> tags = new HashSet<>();
                String tag = scanner.nextLine();
                String count = "yes";
                while(count == "yes") {
                    if (tag.equals("VEGAN")) {
                        tags.add(Ingredient.dietary_tags.VEGAN);
                    } else if (tag.equals("KOSHER")) {
                        tags.add(Ingredient.dietary_tags.KOSHER);
                    } else if (tag.equals("GLUTEN_FREE")) {
                        tags.add(Ingredient.dietary_tags.GLUTEN_FREE);
                    } else if (tag.equals("HALAL")) {
                        tags.add(Ingredient.dietary_tags.HALAL);
                    } else if (tag.equals("NUT_FREE")) {
                        tags.add(Ingredient.dietary_tags.NUT_FREE);
                    } else if (tag.equals("VEGETARIAN")) {
                        tags.add(Ingredient.dietary_tags.VEGETARIAN);
                    } else if (tag.equals("DAIRY_FREE")) {
                        tags.add(Ingredient.dietary_tags.DAIRY_FREE);
                    }
                    System.out.println("Do you want to add more tags?");
                    if (scanner.nextLine().equals("yes")) {
                        count = scanner.nextLine();
                    }
                    else{
                        count = "no";
                    }
                }
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
