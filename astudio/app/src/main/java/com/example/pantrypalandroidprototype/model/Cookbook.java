package com.example.pantrypalandroidprototype.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code Cookbook} class represents a collection of recipes. It provides functionality
 * to add, search, and manage recipes in a cookbook.
 */
public class Cookbook implements Serializable {
    /**
     * A map that stores the list of recipes in the cookbook.
     * The key is the recipe name, and the value is the corresponding {@link Recipe} object.
     */
    public Map<String, Recipe> recipeList = new HashMap<>();

    /**
     * Default constructor that initializes the cookbook with a set of default recipes.
     */
    public Cookbook() {
        initializeDefaultRecipes();
    }


    /**
     * Constructs a {@code Cookbook} with a specified set of recipes.
     *
     * @param recipes A set of {@link Recipe} objects to be added to the cookbook.
     */
    public Cookbook(Set<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            addRecipe(recipe);
        }
    }

    /**
     * Adds a new recipe to the cookbook.
     *
     * @param recipe The {@link Recipe} object to be added.
     */
    public void addRecipe(Recipe recipe) {
        recipeList.put(recipe.recipeName, recipe);
    }


    /**
     * Searches for recipes whose names contain the specified search term.
     * <p>
     * The search is case-insensitive and returns a set of matching recipes.
     * </p>
     *
     * @param name The search term used to filter recipe names.
     * @return A set of {@link Recipe} objects whose names contain the search term.
     */
    public Set<Recipe> searchRecipes(String name) {
        Set<Recipe> foundRecipes = new HashSet<>();
        for (String key : recipeList.keySet()) {
            if (key.toLowerCase().contains(name.toLowerCase())) {
                foundRecipes.add(recipeList.get(key));
            }
        }
        return foundRecipes;
    }

    public void removeRecipe(Recipe recipe) {
        recipeList.remove(recipe.recipeName);
    }


    /**
     * Initializes the cookbook with a predefined set of recipes.
     * <p>
     * The default recipes include various cuisines and dietary preferences, such as
     * vegetarian, vegan, gluten-free, and more. Each recipe contains ingredients,
     * cooking instructions, a description, and other details.
     * </p>
     */
    public void initializeDefaultRecipes() {
        // Recipe 1: Spaghetti Bolognese
        addRecipe(new RecipeBuilder()
                .setName("Spaghetti Bolognese")
                .addIngredient("Spaghetti", 3, "cups", null)
                .addIngredient("Ground Beef", 1, "lb", null)
                .addIngredient("Tomato Sauce", 1, "cup", null)
                .addIngredient("Onion", 1, "medium", null)
                .addIngredient("Garlic", 2, "cloves",  null)
                .addIngredient("Olive Oil", 2, "tbsp",  null)
                .addInstruction("Boil spaghetti in salted water until al dente, reserving 1 cup of pasta water.")
                .addInstruction("Heat olive oil in a pan over medium heat. Sauté chopped onion and minced garlic until fragrant.")
                .addInstruction("Add ground beef and cook until browned, breaking it up as it cooks. Season with salt and pepper.")
                .addInstruction("Stir in tomato sauce and simmer for 15 minutes, adding reserved pasta water to adjust sauce consistency.")
                .addInstruction("Serve the sauce over spaghetti with a sprinkle of grated Parmesan.")
                .addTag("DAIRY FREE")
                .setDescription("A hearty Italian classic with a rich, savory sauce over pasta.")
                .setCookTime(Duration.ofMinutes(40))
                .setServingSize(4)
                .setUrl("https://example.com/spaghetti-bolognese")
                .build());

        // Recipe 2: Caesar Salad
        addRecipe(new RecipeBuilder()
                .setName("Caesar Salad")
                .addIngredient("Romaine Lettuce", 2, "hearts",  null)
                .addIngredient("Caesar Dressing", 0.5, "cup",  null)
                .addIngredient("Croutons", 1, "cup",  null)
                .addIngredient("Parmesan Cheese", 0.25, "cup",  null)
                .addIngredient("Lemon Juice", 1, "tbsp",  null)
                .addInstruction("Wash and chop romaine lettuce into bite-sized pieces.")
                .addInstruction("Toss lettuce with Caesar dressing, croutons, lemon juice, and grated Parmesan.")
                .addInstruction("Serve immediately, garnished with freshly ground black pepper.")
                .setDescription("A crisp, tangy salad with a creamy dressing and crunchy croutons.")
                .setCookTime(Duration.ofMinutes(15))
                .setServingSize(4)
                .setUrl("https://example.com/caesar-salad")
                .build());

        // Recipe 3: Veggie Stir Fry
        addRecipe(new RecipeBuilder()
                .setName("Veggie Stir-Fry")
                .addIngredient("Bell Peppers", 2, "cups",  null)
                .addIngredient("Carrots", 2, "cups",  null)
                .addIngredient("Soy Sauce", 3, "tbsp",  null)
                .addIngredient("Garlic", 2, "cloves",  null)
                .addIngredient("Olive Oil", 2, "tbsp",  null)
                .addIngredient("Ginger", 1, "inch",  null)
                .addInstruction("Heat olive oil in a large skillet or wok over medium-high heat.")
                .addInstruction("Add chopped garlic and ginger, then sauté until fragrant.")
                .addInstruction("Add mixed vegetables and stir-fry until tender-crisp, about 5-7 minutes.")
                .addInstruction("Pour in soy sauce and stir to combine.")
                .addInstruction("Serve over rice or noodles.")
                .setDescription("A quick and healthy stir-fry with vibrant vegetables and savory soy sauce.")
                .setCookTime(Duration.ofMinutes(20))
                .setServingSize(4)
                .setUrl("https://example.com/veggie-stir-fry")
                .build());

        // Recipe 4: Chicken Curry
        addRecipe(new RecipeBuilder()
                .setName("Chicken Curry")
                .addIngredient("Chicken Breast", 2, "pieces",  null)
                .addIngredient("Curry Powder", 2, "tbsp",  null)
                .addIngredient("Coconut Milk", 1, "can (400 ml)",  null)
                .addIngredient("Onion", 1, "large",  null)
                .addIngredient("Garlic", 3, "cloves",  null)
                .addIngredient("Tomatoes", 2, "medium",  null)
                .addInstruction("Dice onions and tomatoes, mince garlic.")
                .addInstruction("Cook chicken in a large pot over medium heat until lightly browned.")
                .addInstruction("Add onion, garlic, curry powder, and tomatoes; cook for 5 minutes.")
                .addInstruction("Stir in coconut milk and simmer for 25 minutes.")
                .addInstruction("Serve with steamed rice and garnish with fresh cilantro.")
                .setDescription("A creamy and aromatic chicken curry with bold, rich flavors.")
                .setCookTime(Duration.ofMinutes(40))
                .setServingSize(4)
                .setUrl("https://example.com/chicken-curry")
                .build());

        // Recipe 5: Pancakes
        addRecipe(new RecipeBuilder()
                .setName("Pancakes")
                .addIngredient("Flour", 2, "cups",  null)
                .addIngredient("Almond Milk", 1, "cup",  null)
                .addIngredient("Baking Powder", 2, "tsp",  null)
                .addIngredient("Salt", 0.5, "tsp",  null)
                .addIngredient("Sugar", 2, "tbsp",  null)
                .addInstruction("Mix all dry ingredients (flour, baking powder, salt, sugar) in a bowl.")
                .addInstruction("Add almond milk to the dry ingredients and stir until smooth.")
                .addInstruction("Heat a non-stick griddle or frying pan over medium heat. Pour batter onto the griddle in small circles.")
                .addInstruction("Cook pancakes until bubbles form on the surface, then flip and cook until golden brown on both sides.")
                .addInstruction("Serve with maple syrup, fresh fruit, and a sprinkle of powdered sugar.")
                .setDescription("Fluffy vegan pancakes, perfect for breakfast or brunch.")
                .setCookTime(Duration.ofMinutes(15))
                .setServingSize(4)
                .setUrl("https://example.com/pancakes")
                .build());

        // Recipe 6: Quinoa Salad
        addRecipe(new RecipeBuilder()
                .setName("Quinoa Salad")
                .addIngredient("Quinoa", 1, "cup",  null)
                .addIngredient("Cherry Tomatoes", 12, "whole",  null)
                .addIngredient("Cucumber", 1, "large",  null)
                .addIngredient("Olive Oil", 3, "tbsp",  null)
                .addIngredient("Lemon Juice", 2, "tbsp",  null)
                .addIngredient("Fresh Parsley", 0.5, "cup",  null)
                .addInstruction("Rinse quinoa under cold water and cook according to package instructions (usually 15 minutes).")
                .addInstruction("Dice cucumber and halve the cherry tomatoes.")
                .addInstruction("Whisk olive oil and lemon juice in a small bowl to make the dressing.")
                .addInstruction("Chop fresh parsley and add it to the quinoa mixture along with the vegetables.")
                .addInstruction("Toss everything together and drizzle with the dressing.")
                .setDescription("A refreshing salad made with quinoa, fresh veggies, and a zesty lemon dressing.")
                .setCookTime(Duration.ofMinutes(25))
                .setServingSize(4)
                .setUrl("https://example.com/quinoa-salad")
                .build());

        // Recipe 7: Beef Tacos
        addRecipe(new RecipeBuilder()
                .setName("Beef Tacos")
                .addIngredient("Ground Beef", 1, "lb",  null)
                .addIngredient("Taco Shells", 12, "pieces",  null)
                .addIngredient("Lettuce", 1, "head",  null)
                .addIngredient("Cheddar Cheese", 1, "cup",  null)
                .addIngredient("Sour Cream", 0.5, "cup",  null)
                .addIngredient("Taco Seasoning", 1, "pkg",  null)
                .addIngredient("Tomato", 1, "whole",  null)
                .addInstruction("Brown the ground beef in a large pan over medium heat.")
                .addInstruction("Drain excess fat and add taco seasoning. Follow package instructions, adding water as directed.")
                .addInstruction("While the beef cooks, shred the lettuce and chop the tomato into small pieces.")
                .addInstruction("Fill taco shells with the seasoned beef, then top with shredded lettuce, diced tomato, cheese, and sour cream.")
                .setDescription("Delicious beef tacos with fresh toppings and a tangy taco seasoning.")
                .setCookTime(Duration.ofMinutes(30))
                .setServingSize(4)
                .setUrl("https://example.com/beef-tacos")
                .build());

        // Recipe 8: Vegetable Soup
        addRecipe(new RecipeBuilder()
                .setName("Vegetable Soup")
                .addIngredient("Carrots", 3, "medium",  null)
                .addIngredient("Potatoes", 3, "medium",  null)
                .addIngredient("Celery Stalks", 2, "whole",  null)
                .addIngredient("Vegetable Broth", 3, "cups",  null)
                .addIngredient("Bay Leaf", 1, "leaf",  null)
                .addIngredient("Onion", 1, "medium",  null)
                .addIngredient("Garlic", 2, "cloves",  null)
                .addIngredient("Olive Oil", 2, "tablespoons", null)
                .addIngredient("Salt", 1, "teaspoon",  null)
                .addIngredient("Pepper", 1, "teaspoon",  null)
                .addInstruction("Peel and dice carrots, potatoes, and celery into bite-sized pieces.")
                .addInstruction("Dice the onion and garlic.")
                .addInstruction("In a large pot, heat olive oil over medium heat, then sauté onion and garlic for 2-3 minutes.")
                .addInstruction("Add vegetable broth, bay leaf, and diced vegetables to the pot. Bring to a boil.")
                .addInstruction("Reduce heat, cover, and simmer for 30-40 minutes until vegetables are tender.")
                .addInstruction("Remove bay leaf, season with salt and pepper to taste, and serve warm.")
                .setDescription("A wholesome soup brimming with hearty vegetables and comforting flavors.")
                .setCookTime(Duration.ofMinutes(45))
                .setServingSize(6)
                .setUrl("https://example.com/vegetable-soup")
                .build());

        // Recipe 9: Chocolate Cake
        addRecipe(new RecipeBuilder()
                .setName("Chocolate Cake")
                .addIngredient("Flour", 1, "cup",  null)
                .addIngredient("Cocoa Powder", 0.33, "cup",  null)
                .addIngredient("Sugar", 0.75, "cup",  null)
                .addIngredient("Baking Powder", 1, "teaspoon",  null)
                .addIngredient("Baking Soda", 0.5, "teaspoon",  null)
                .addIngredient("Salt", 0.5, "teaspoon",  null)
                .addIngredient("Vegetable Oil", 0.5, "cup",  null)
                .addIngredient("Apple Cider Vinegar", 1, "tablespoon",  null)
                .addIngredient("Vanilla Extract", 1, "teaspoon",  null)
                .addIngredient("Water", 1, "cup",  null)
                .addInstruction("Preheat oven to 350°F (175°C). Grease and flour an 8-inch round cake pan.")
                .addInstruction("In a large bowl, sift together flour, cocoa powder, sugar, baking powder, baking soda, and salt.")
                .addInstruction("In a separate bowl, mix together oil, vinegar, vanilla extract, and water.")
                .addInstruction("Pour wet ingredients into dry ingredients and stir until well combined.")
                .addInstruction("Pour the batter into the prepared pan and bake for 30-35 minutes or until a toothpick inserted in the center comes out clean.")
                .addInstruction("Let the cake cool in the pan for 10 minutes, then transfer to a wire rack to cool completely.")
                .setDescription("A rich, moist chocolate cake perfect for any occasion.")
                .setCookTime(Duration.ofMinutes(40))
                .setServingSize(8)
                .setUrl("https://example.com/chocolate-cake")
                .build());

        // Recipe 10: Grilled Cheese Sandwich
        addRecipe(new RecipeBuilder()
                .setName("Grilled Cheese Sandwich")
                .addIngredient("Bread", 2, "slices",  null)
                .addIngredient("Cheese", 2, "slices",  null)
                .addInstruction("Place cheese slices between the bread.")
                .addInstruction("Heat a skillet over medium heat and place the sandwich in the skillet.")
                .addInstruction("Cook for 2-3 minutes on each side, pressing down lightly, until the bread is golden brown and the cheese is melted.")
                .addInstruction("Remove from skillet and serve warm.")
                .setDescription("A simple and comforting grilled cheese sandwich.")
                .setCookTime(Duration.ofMinutes(10))
                .setServingSize(1)
                .setUrl("https://example.com/grilled-cheese-sandwich")
                .build());
    }
}
