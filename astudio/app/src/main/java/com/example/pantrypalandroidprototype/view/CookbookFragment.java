package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.FragmentCookbookBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.example.pantrypalandroidprototype.model.RecipeBuilder;
import com.example.pantrypalandroidprototype.model.RecipeAdapter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CookbookFragment extends Fragment implements ICookbookView, RecipeAdapter.OnRecipeClickListener {

    FragmentCookbookBinding binding;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    Set<Recipe> recipes;
    Listener listener;

    public static CookbookFragment newInstance(Listener listener, Set<Recipe> recipes) {
        CookbookFragment fragment = new CookbookFragment();
        fragment.listener = listener;
        fragment.recipes = recipes;  // Set the passed recipes
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCookbookBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Set up Add Recipe button
        binding.addRecipeButton.setOnClickListener(v -> {
            // Navigate to AddRecipeFragment
            navigateToAddRecipe();
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            recipes = (Set<Recipe>) getArguments().getSerializable("recipes");
        }

        // Notify the listener (ControllerActivity) of the recipes
        if (listener != null) {
            listener.onCookbookRecipesLoaded(recipes);

            recyclerView = view.findViewById(R.id.recycler_view_recipes);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // Initialize the adapter and set it to the RecyclerView
            recipeAdapter = new RecipeAdapter(new ArrayList<>(recipes), getContext(), this);
            recyclerView.setAdapter(recipeAdapter);
        }
    }

    public static Set<Recipe> getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();

        // Recipe 1: Spaghetti Bolognese
        recipes.add(new RecipeBuilder()
                .setName("Spaghetti Bolognese")
                .addIngredient("Spaghetti", 200, "g", Set.of(Ingredient.dietary_tags.VEGETARIAN))
                .addIngredient("Ground Beef", 250, "g", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Tomato Sauce", 150, "ml", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Boil spaghetti until al dente.")
                .addInstruction("Brown ground beef in a pan.")
                .addInstruction("Add tomato sauce and simmer for 10 minutes.")
                .setDescription("A classic Italian dish made with beef and a rich tomato sauce.")
                .setCookTime(Duration.ofMinutes(30))
                .setServingSize(2)
                .setUrl("https://example.com/spaghetti-bolognese")
                .build());

        // Recipe 2: Caesar Salad
        recipes.add(new RecipeBuilder()
                .setName("Caesar Salad")
                .addIngredient("Lettuce", 1, "head", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Caesar Dressing", 50, "ml", Set.of(Ingredient.dietary_tags.DAIRY_FREE))
                .addIngredient("Croutons", 30, "g", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Tear lettuce into pieces.")
                .addInstruction("Mix with Caesar dressing and croutons.")
                .setDescription("A fresh salad with creamy Caesar dressing and crispy croutons.")
                .setCookTime(Duration.ofMinutes(10))
                .setServingSize(2)
                .setUrl("https://example.com/caesar-salad")
                .build());

        // Recipe 3: Veggie Stir Fry
        recipes.add(new RecipeBuilder()
                .setName("Veggie Stir Fry")
                .addIngredient("Bell Peppers", 1, "whole", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Carrot", 1, "whole", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Soy Sauce", 30, "ml", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addInstruction("Chop the vegetables into thin strips.")
                .addInstruction("Stir fry vegetables in a hot pan for 5 minutes.")
                .addInstruction("Add soy sauce and cook for an additional 2 minutes.")
                .setDescription("A colorful and healthy stir fry with a soy-based sauce.")
                .setCookTime(Duration.ofMinutes(15))
                .setServingSize(2)
                .setUrl("https://example.com/veggie-stir-fry")
                .build());

        // Recipe 4: Chicken Curry
        recipes.add(new RecipeBuilder()
                .setName("Chicken Curry")
                .addIngredient("Chicken Breast", 300, "g", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Curry Powder", 20, "g", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Coconut Milk", 200, "ml", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.DAIRY_FREE))
                .addInstruction("Cook chicken in a pot until browned.")
                .addInstruction("Add curry powder and stir for 1 minute.")
                .addInstruction("Pour in coconut milk and simmer for 20 minutes.")
                .setDescription("A rich and creamy curry with tender chicken pieces.")
                .setCookTime(Duration.ofMinutes(30))
                .setServingSize(4)
                .setUrl("https://example.com/chicken-curry")
                .build());

        // Recipe 5: Pancakes
        recipes.add(new RecipeBuilder()
                .setName("Pancakes")
                .addIngredient("Flour", 200, "g", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Almond Milk", 250, "ml", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.DAIRY_FREE))
                .addIngredient("Baking Powder", 10, "g", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Mix all dry ingredients in a bowl.")
                .addInstruction("Add almond milk and stir to form a batter.")
                .addInstruction("Cook on a hot griddle until golden brown on both sides.")
                .setDescription("Fluffy vegan pancakes, perfect for breakfast.")
                .setCookTime(Duration.ofMinutes(15))
                .setServingSize(4)
                .setUrl("https://example.com/pancakes")
                .build());

        // Recipe 6: Quinoa Salad
        recipes.add(new RecipeBuilder()
                .setName("Quinoa Salad")
                .addIngredient("Quinoa", 100, "g", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Cherry Tomatoes", 10, "whole", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Olive Oil", 30, "ml", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Cook quinoa according to package instructions.")
                .addInstruction("Chop tomatoes and add to the cooked quinoa.")
                .addInstruction("Toss with olive oil and serve.")
                .setDescription("A simple yet refreshing salad with quinoa and tomatoes.")
                .setCookTime(Duration.ofMinutes(20))
                .setServingSize(2)
                .setUrl("https://example.com/quinoa-salad")
                .build());

        // Recipe 7: Beef Tacos
        recipes.add(new RecipeBuilder()
                .setName("Beef Tacos")
                .addIngredient("Ground Beef", 250, "g", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Taco Shells", 4, "pieces", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Lettuce", 1, "leaf", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Brown the ground beef in a pan.")
                .addInstruction("Fill taco shells with beef and top with lettuce.")
                .setDescription("Delicious tacos with seasoned beef and fresh lettuce.")
                .setCookTime(Duration.ofMinutes(20))
                .setServingSize(2)
                .setUrl("https://example.com/beef-tacos")
                .build());

        // Recipe 8: Vegetable Soup
        recipes.add(new RecipeBuilder()
                .setName("Vegetable Soup")
                .addIngredient("Carrot", 1, "whole", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Potato", 2, "medium", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Vegetable Broth", 500, "ml", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Chop the vegetables and add to a pot with vegetable broth.")
                .addInstruction("Simmer for 30 minutes or until vegetables are tender.")
                .setDescription("A warm and hearty soup made with seasonal vegetables.")
                .setCookTime(Duration.ofMinutes(40))
                .setServingSize(4)
                .setUrl("https://example.com/vegetable-soup")
                .build());

        // Recipe 9: Chocolate Cake
        recipes.add(new RecipeBuilder()
                .setName("Chocolate Cake")
                .addIngredient("Flour", 200, "g", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Cocoa Powder", 50, "g", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Sugar", 150, "g", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Preheat oven to 180°C.")
                .addInstruction("Mix all ingredients in a bowl.")
                .addInstruction("Bake for 30 minutes or until a toothpick comes out clean.")
                .setDescription("A rich, moist chocolate cake perfect for any occasion.")
                .setCookTime(Duration.ofMinutes(40))
                .setServingSize(8)
                .setUrl("https://example.com/chocolate-cake")
                .build());

        // Recipe 10: Grilled Cheese Sandwich
        recipes.add(new RecipeBuilder()
                .setName("Grilled Cheese Sandwich")
                .addIngredient("Bread", 2, "slices", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Cheese", 2, "slices", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Butter the bread and place cheese between slices.")
                .addInstruction("Grill the sandwich until golden brown on both sides.")
                .setDescription("A simple and comforting grilled cheese sandwich.")
                .setCookTime(Duration.ofMinutes(10))
                .setServingSize(1)
                .setUrl("https://example.com/grilled-cheese-sandwich")
                .build());

        return recipes;
    }

    public void onViewCookbookMenu(){
        if (listener != null) {
            listener.onViewCookbookMenu();
        }
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        if (listener != null) {
            listener.onRecipeClick(recipe);
        }
    }

    public void navigateToAddRecipe() {
        if (listener != null) {
            listener.onNavigateToAddRecipe();
        }
    }

    @Override
    public void onCookbookRecipesLoaded(Set<Recipe> recipes) {
        this.recipes = recipes;
        if (recipeAdapter != null) {
            recipeAdapter.updateRecipes(new ArrayList<>(recipes)); // Refresh the data in the adapter
            recipeAdapter.notifyDataSetChanged(); // Notify changes
        }
    }
}