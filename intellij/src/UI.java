import java.time.Duration;
import java.util.*;

public class UI {
    private Controller controller;
    private Scanner scanner;

    public UI(Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }


    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n______________________________________________________________________\n");
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Manage Pantry");
            System.out.println("2. Generate Recipe Suggestions");
            System.out.println("3. Upload a Recipe");
            System.out.println("4. View Cookbook");
            System.out.println("5. Search Recipe by Name");
            System.out.println("0. Exit");
            System.out.println("\n______________________________________________________________________\n");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    managePantry();
                    break;
                case 2:
                    controller.generateRecipeSuggestions();
                    break;
                case 3:
                    uploadRecipeUI();
                    break;
                case 4:
                    controller.viewCookbook();
                    break;
                case 5:
                    searchRecipeUI();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thanks for using your Pantry Pal!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    public void managePantry() {
        boolean managingPantry = true;

        while (managingPantry) {
            System.out.println("\n______________________________________________________________________\n");
            System.out.println("\n--- Pantry Management ---\n");
            controller.viewPantryContents();System.out.println("\n______________________________________________________________________\n");

            System.out.println("1. Add Ingredient");
            System.out.println("2. Edit Ingredient Quantity");
            System.out.println("3. Delete Ingredient");
            System.out.println("4. Search for Ingredient by Name");
            System.out.println("5. Filter Ingredients by Dietary Tag");
            System.out.println("6. Back to Main Menu");
            System.out.println("\n______________________________________________________________________\n");

            String choiceStr = scanner.nextLine().trim();  // Read input as String

            if (!choiceStr.matches("[1-6]")) {  // Check if input is a valid option
                System.out.println("Invalid option. Please enter a number between 1 and 6.");
                continue;  // Loop again until a valid option is entered
            }

            int choice = Integer.parseInt(choiceStr);  // Parse valid input as integer

            switch (choice) {
                case 1:
                    addIngredientUI();
                    break;
                case 2:
                    editIngredientUI();
                    break;
                case 3:
                    deleteIngredientUI();
                    break;
                case 4:
                    searchIngredientUI();
                    break;
                case 5:
                    filterByDietaryTagUI();
                    break;
                case 6:
                    managingPantry = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public void addIngredientUI() {
        String name = getIngredientName();
        int quantity = getIngredientQuantity();
        String unit = getIngredientUnit();
        Set<Ingredient.dietary_tags> tags = getIngredientTags();

        controller.addIngredient(name, quantity, unit, tags);
    }

    public String getIngredientName() {
        System.out.print("Enter ingredient name: ");
        return scanner.nextLine();
    }

    public int getIngredientQuantity() {
        int quantity = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter quantity: ");
            try {
                quantity = scanner.nextInt();
                scanner.nextLine();
                if (quantity <= 0) {
                    System.out.println("Quantity must be a positive number. Please enter a valid quantity.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number for quantity.");
                scanner.nextLine();
            }
        }
        return quantity;
    }

    public String getIngredientUnit() {
        System.out.print("Enter unit: ");
        return scanner.nextLine();
    }

    public Set<Ingredient.dietary_tags> getIngredientTags() {
        Set<Ingredient.dietary_tags> tags = new HashSet<>();
        System.out.println("Available tags: [VEGAN, KOSHER, GLUTEN_FREE, HALAL, NUT_FREE, VEGETARIAN, DAIRY_FREE]");
        System.out.println("Add dietary tags (comma-separated, e.g., VEGAN,KOSHER) or leave blank:");

        String tagsInput = scanner.nextLine();
        if (!tagsInput.isEmpty()) {
            for (String tag : tagsInput.split(",")) {
                try {
                    tags.add(Ingredient.dietary_tags.valueOf(tag.trim().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid tag: " + tag + ". Skipping this tag.");
                }
            }
        }
        return tags;
    }


    public void editIngredientUI() {
        System.out.print("Enter ingredient name to edit: ");
        String name = scanner.nextLine();

        System.out.print("Enter new quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine();

        controller.editIngredient(name, newQuantity);
    }

    public void deleteIngredientUI() {
        System.out.print("Enter ingredient name to delete: ");
        String name = scanner.nextLine();

        controller.deleteIngredient(name);
    }

    public void searchIngredientUI() {
        System.out.print("Enter ingredient name to search: ");
        String name = scanner.nextLine();
        controller.searchIngredientByName(name);
    }

    public void filterByDietaryTagUI() {
        boolean validTag = false;

        while (!validTag) {
            System.out.println("Enter a dietary tag to filter by (e.g., VEGAN): ");
            System.out.println("Available tags: " + EnumSet.allOf(Ingredient.dietary_tags.class));  // Show all valid tags
            String tagName = scanner.nextLine().trim().toUpperCase();

            if (tagName.isEmpty()) {  // Allow users to exit filter by pressing Enter without input
                System.out.println("No tag entered. Returning to Pantry Management.");
                return;
            }

            try {
                Ingredient.dietary_tags tag = Ingredient.dietary_tags.valueOf(tagName);
                controller.printIngredientsByTag(tag);
                validTag = true;  // Exit loop after successful filtering
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid tag entered. Please try again.");
            }
        }
    }

    public void uploadRecipeUI() {
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        int cookTimeMinutes = getPositiveInteger("Enter cook time (minutes): ", "Cook time cannot be negative. Please enter a valid cook time.");
        int servingSize = getPositiveInteger("Enter serving size: ", "Serving size must be a positive number. Please enter a valid serving size.");

        Set<Ingredient> ingredients = new HashSet<>();
        System.out.println("Add ingredients (enter 'done' to finish):");
        while (true) {
            String ingredientName = getIngredientName();
            if (ingredientName.equalsIgnoreCase("done")) {
                break;
            }

            int quantity = getIngredientQuantity();
            String unit = getIngredientUnit();
            Set<Ingredient.dietary_tags> tags = getIngredientTags();
            ingredients.add(new Ingredient(ingredientName, quantity, unit, tags));
        }

        System.out.println("Add cooking instructions (enter 'done' to finish):");
        List<String> instructions = new ArrayList<>();
        while (true) {
            String instruction = scanner.nextLine();
            if (instruction.equalsIgnoreCase("done")) {
                break;
            }
            instructions.add(instruction);
        }

        controller.uploadRecipe(name, description, Duration.ofMinutes(cookTimeMinutes), servingSize, ingredients, new HashSet<>(instructions));
    }

    public int getPositiveInteger(String prompt, String errorMessage) {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (value < 0) {
                    System.out.println(errorMessage);
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return value;
    }

    public void searchRecipeUI() {
        System.out.print("Enter recipe name to search: ");
        String name = scanner.nextLine();
        controller.searchRecipeByName(name);
    }

}

