package com.example.pantrypalandroidprototype.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.room.Room;

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
import com.example.pantrypalandroidprototype.model.RecipeDatabase;
import com.example.pantrypalandroidprototype.model.RecipeService;
import com.google.android.material.snackbar.Snackbar;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CookbookFragment extends Fragment implements ICookbookView, RecipeAdapter.OnRecipeClickListener {

    FragmentCookbookBinding binding;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    Set<Recipe> recipes;
    Listener listener;
    RecipeDatabase recipeDatabase;

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

        // Fetch recipes from EDAMAM API
        fetchRecipesFromAPI();
        recipeDatabase = Room.databaseBuilder(requireContext(), RecipeDatabase.class, "recipes_db").build();

        // Set up Add Recipe button
        binding.addRecipeButton.setOnClickListener(v -> {
            // Navigate to AddRecipeFragment
            navigateToAddRecipe();
        });

        setupRecyclerView();
        // Add click listener for the Search Recipes button
        binding.searchRecipesButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSearchRecipesMenu(); // Navigate to fragment
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            recipes = (Set<Recipe>) getArguments().getSerializable("recipes");
        }

        if (listener != null) {
            listener.onCookbookRecipesLoaded(recipes);

            recyclerView = view.findViewById(R.id.recycler_view_recipes);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // Initialize the adapter and set it to the RecyclerView
            recipeAdapter = new RecipeAdapter(new ArrayList<>(recipes), requireContext(), this);
            recyclerView.setAdapter(recipeAdapter);
        }
    }

    public void fetchRecipesFromDatabase() {
        new Thread(() -> {
            List<Recipe> dbRecipes = recipeDatabase.recipeDao().getAllRecipes();

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    recipes.clear();
                    recipes.addAll(dbRecipes);
                    recipeAdapter.updateRecipes(new ArrayList<>(recipes));
                });
            }
        }).start();
    }
    @Override
    public void onRecipeCreated(Recipe recipe) {
        new Thread(() -> {
            recipeDatabase.recipeDao().insertRecipe(recipe);

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    recipes.add(recipe);
                    recipeAdapter.updateRecipes(new ArrayList<>(recipes));
                });
            }
        }).start();
    }

    @Override
    public void onSearchRecipesMenu() {
        // Show a dialog or navigate to a search fragment
        showSearchDialog();
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Search Recipes");

        final EditText input = new EditText(requireContext());
        builder.setView(input);

        builder.setPositiveButton("Search", (dialog, which) -> {
            String query = input.getText().toString();
            new Thread(() -> {
                List<Recipe> searchResults = recipeDatabase.recipeDao().searchRecipes("%" + query + "%");
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        recipeAdapter.updateRecipes(searchResults);
                    });
                }
            }).start();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public void fetchRecipesFromAPI() {
        new Thread(() -> {
            try {
                // Call API Service to fetch recipes
                RecipeService recipeService = new RecipeService();
                List<Recipe> apiRecipes = recipeService.fetchRecipes("chicken"); // Example query

                // Update UI with recipes
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        recipes.addAll(apiRecipes);
                        recipeAdapter = new RecipeAdapter(new ArrayList<>(recipes), requireContext(), this);
                        recyclerView.setAdapter(recipeAdapter);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        Snackbar.make(binding.getRoot(), "Failed to load recipes.", Snackbar.LENGTH_LONG).show();
                    });
                }
            }
        }).start();
    }

    public static Set<Recipe> getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        // Initializing pre-loaded recipes

        // Recipe 1: Spaghetti Bolognese
        recipes.add(new RecipeBuilder()
                .setName("Spaghetti Bolognese")
                .addIngredient("Spaghetti", 300, "g", Set.of(Ingredient.dietary_tags.VEGETARIAN))
                .addIngredient("Ground Beef", 350, "g", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Tomato Sauce", 400, "ml", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Onion", 1, "medium", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Garlic", 2, "cloves", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Olive Oil", 2, "tbsp", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Boil spaghetti until al dente, reserving 1 cup of pasta water.")
                .addInstruction("Heat olive oil in a pan, sauté onion and garlic until fragrant.")
                .addInstruction("Brown the ground beef and season with salt and pepper.")
                .addInstruction("Stir in tomato sauce and simmer with reserved pasta water for 15 minutes.")
                .addInstruction("Serve sauce over spaghetti with grated Parmesan cheese.")
                .setDescription("A hearty Italian classic with rich, flavorful sauce.")
                .setCookTime(Duration.ofMinutes(40))
                .setServingSize(4)
                .setUrl("https://example.com/spaghetti-bolognese")
                .build());

// Recipe 2: Caesar Salad
        recipes.add(new RecipeBuilder()
                .setName("Caesar Salad")
                .addIngredient("Romaine Lettuce", 2, "hearts", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Caesar Dressing", 100, "ml", Set.of(Ingredient.dietary_tags.DAIRY_FREE))
                .addIngredient("Croutons", 50, "g", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Parmesan Cheese", 30, "g", Set.of())
                .addIngredient("Lemon Juice", 1, "tbsp", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Wash and chop lettuce into bite-sized pieces.")
                .addInstruction("Toss lettuce with dressing, croutons, lemon juice, and Parmesan.")
                .addInstruction("Serve immediately, garnished with freshly ground black pepper.")
                .setDescription("A crisp, tangy salad with a creamy dressing.")
                .setCookTime(Duration.ofMinutes(15))
                .setServingSize(4)
                .setUrl("https://example.com/caesar-salad")
                .build());

// Recipe 3: Veggie Stir Fry
        recipes.add(new RecipeBuilder()
                .setName("Veggie Stir Fry")
                .addIngredient("Bell Peppers", 2, "medium", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Carrot", 2, "medium", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Soy Sauce", 50, "ml", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Ginger", 1, "inch", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Sesame Oil", 1, "tbsp", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Broccoli Florets", 200, "g", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.GLUTEN_FREE))
                .addInstruction("Chop vegetables into thin strips or bite-sized pieces.")
                .addInstruction("Heat sesame oil in a wok and sauté ginger until fragrant.")
                .addInstruction("Add all vegetables and stir fry on high heat for 5-7 minutes.")
                .addInstruction("Stir in soy sauce and cook for an additional 2 minutes.")
                .addInstruction("Serve hot with steamed rice or noodles.")
                .setDescription("A vibrant and healthy stir fry loaded with fresh vegetables.")
                .setCookTime(Duration.ofMinutes(20))
                .setServingSize(4)
                .setUrl("https://example.com/veggie-stir-fry")
                .build());

// Recipe 4: Chicken Curry
        recipes.add(new RecipeBuilder()
                .setName("Chicken Curry")
                .addIngredient("Chicken Breast", 500, "g", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Curry Powder", 25, "g", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Coconut Milk", 400, "ml", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.DAIRY_FREE))
                .addIngredient("Onion", 1, "large", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Garlic", 2, "cloves", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Tomatoes", 2, "medium", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Dice onions and tomatoes, mince garlic.")
                .addInstruction("Cook chicken in a large pot until lightly browned.")
                .addInstruction("Add onion, garlic, curry powder, and tomatoes; cook for 5 minutes.")
                .addInstruction("Stir in coconut milk and simmer for 25 minutes.")
                .addInstruction("Serve with steamed rice and garnish with fresh cilantro.")
                .setDescription("A creamy and aromatic chicken curry with bold flavors.")
                .setCookTime(Duration.ofMinutes(40))
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
                .addIngredient("Tri-Color Quinoa", 150, "g", Set.of(Ingredient.dietary_tags.VEGAN, Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Cherry Tomatoes", 12, "whole", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Cucumber", 1, "large", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Extra Virgin Olive Oil", 45, "ml", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Lemon Juice", 30, "ml", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Rinse quinoa under cold water and cook according to package instructions.")
                .addInstruction("Dice cucumber and halve the cherry tomatoes.")
                .addInstruction("Whisk olive oil and lemon juice to make the dressing.")
                .addInstruction("Combine cooked quinoa, vegetables, and dressing in a bowl. Toss well.")
                .setDescription("A protein-packed salad with fresh vegetables and a zesty lemon dressing.")
                .setCookTime(Duration.ofMinutes(25))
                .setServingSize(4)
                .setUrl("https://example.com/quinoa-salad")
                .build());

        // Recipe 7: Beef Tacos
        recipes.add(new RecipeBuilder()
                .setName("Beef Tacos")
                .addIngredient("Ground Beef", 300, "g", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Taco Shells", 6, "pieces", Set.of(Ingredient.dietary_tags.GLUTEN_FREE))
                .addIngredient("Iceberg Lettuce", 4, "leaves", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Shredded Cheddar Cheese", 100, "g", Set.of(Ingredient.dietary_tags.VEGETARIAN))
                .addIngredient("Salsa", 100, "ml", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Cook ground beef in a skillet over medium heat until browned.")
                .addInstruction("Season beef with salt, pepper, and taco seasoning. Cook for an additional 2 minutes.")
                .addInstruction("Warm taco shells in the oven or microwave.")
                .addInstruction("Assemble tacos with beef, lettuce, cheese, and salsa.")
                .setDescription("Crispy tacos filled with seasoned beef, fresh lettuce, and zesty toppings.")
                .setCookTime(Duration.ofMinutes(25))
                .setServingSize(3)
                .setUrl("https://example.com/beef-tacos")
                .build());

        // Recipe 8: Vegetable Soup
        recipes.add(new RecipeBuilder()
                .setName("Vegetable Soup")
                .addIngredient("Carrots", 3, "medium", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Potatoes", 3, "medium", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Celery Stalks", 2, "whole", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Vegetable Broth", 750, "ml", Set.of(Ingredient.dietary_tags.VEGAN))
                .addIngredient("Bay Leaf", 1, "piece", Set.of(Ingredient.dietary_tags.VEGAN))
                .addInstruction("Peel and dice carrots, potatoes, and celery into bite-sized pieces.")
                .addInstruction("In a large pot, bring vegetable broth to a boil with a bay leaf.")
                .addInstruction("Add vegetables to the pot and simmer for 30 minutes.")
                .addInstruction("Remove bay leaf, season with salt and pepper, and serve warm.")
                .setDescription("A wholesome soup brimming with hearty vegetables and comforting flavors.")
                .setCookTime(Duration.ofMinutes(45))
                .setServingSize(6)
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
            recipeAdapter.notifyDataSetChanged();
        }
    }

    public void setupRecyclerView() {
        recyclerView = binding.recyclerViewRecipes;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recipeAdapter = new RecipeAdapter(new ArrayList<>(recipes), getContext(), this);
        recyclerView.setAdapter(recipeAdapter);
    }
}
