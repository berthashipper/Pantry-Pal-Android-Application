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
import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.example.pantrypalandroidprototype.model.RecipeBuilder;
import com.example.pantrypalandroidprototype.model.RecipeAdapter;
//import com.example.pantrypalandroidprototype.model.RecipeDatabase;
//import com.example.pantrypalandroidprototype.model.RecipeService;
import com.google.android.material.snackbar.Snackbar;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code CookbookFragment} class represents a fragment displaying a list of recipes.
 * It implements the {@link ICookbookView} interface and handles user interactions with the recipe list.
 */
public class CookbookFragment extends Fragment implements ICookbookView, RecipeAdapter.OnRecipeClickListener {
    /**
     * View binding for accessing the UI elements of the fragment.
     */
    FragmentCookbookBinding binding;
    /**
     * RecyclerView for displaying the list of recipes.
     */
    RecyclerView recyclerView;
    /**
     * Adapter for managing and displaying recipes in the RecyclerView.
     */
    RecipeAdapter recipeAdapter;
    /**
     * The {@link Cookbook} instance that holds the collection of recipes.
     */
    Cookbook cookbook;
    /**
     * Listener for handling fragment interactions, such as navigation and events.
     */
    Listener listener;
    //RecipeDatabase recipeDatabase;


    /**
     * Creates a new instance of {@code CookbookFragment} with a specified listener and cookbook.
     *
     * @param listener The listener to handle fragment interactions.
     * @param cookbook The cookbook containing the recipes to display.
     * @return A new instance of {@code CookbookFragment}.
     */
    public static CookbookFragment newInstance(ICookbookView.Listener listener, Cookbook cookbook) {
        CookbookFragment fragment = new CookbookFragment();
        fragment.listener = listener;
        fragment.cookbook = cookbook;  //Set<Recipe> recipes; Set the passed recipes
        return fragment;
    }

    /**
     * Called to create the fragment's view hierarchy.
     *
     * @param inflater  The {@link LayoutInflater} object for inflating views.
     * @param container The parent view group for the fragment's UI.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     * @return The root view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCookbookBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Fetch recipes from EDAMAM API
        //fetchRecipesFromAPI();
        //recipeDatabase = Room.databaseBuilder(requireContext(), RecipeDatabase.class, "recipes_db").build();

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

    /**
     * Called immediately after the fragment's view has been created.
     *
     * @param view The fragment's root view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*// Set up RecyclerView to ensure the latest recipes are displayed
        if (cookbook != null && recipeAdapter != null) {
            recipeAdapter.updateRecipes(new ArrayList<>(recipes)); // Update adapter's dataset
            recipeAdapter.notifyDataSetChanged(); // Notify adapter of data changes
        }*/

        //setupRecyclerView();
        /*if (listener != null) {
            listener.onCookbookRecipesLoaded(cookbook);
        }*/
    }

    /*public void fetchRecipesFromDatabase() {
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
    }*/

    /**
     * Handles the event when a recipe is created.
     *
     * @param recipe The newly created recipe.
     */
    @Override
    public void onRecipeCreated(Recipe recipe) {
        /*new Thread(() -> {
            recipeDatabase.recipeDao().insertRecipe(recipe);

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    recipes.add(recipe);
                    recipeAdapter.updateRecipes(new ArrayList<>(recipes));
                });
            }
        }).start();*/
    }

    /**
     * Handles the event when the Search Recipes menu is opened.
     */
    @Override
    public void onSearchRecipesMenu() {
        // Show a dialog or navigate to a search fragment
        showSearchDialog();
    }

    /**
     * Displays a dialog for searching recipes by name or keyword.
     */
    private void showSearchDialog() {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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
        builder.show();*/
    }

    /*public void fetchRecipesFromAPI() {
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
    }*/

    /**
     * Retrieves all recipes from the {@link Cookbook}.
     *
     * @return A set containing all recipes in the cookbook.
     */
    public Set<Recipe> getAllRecipes() {
        // Ensure the cookbook is not null
        if (cookbook == null) {
            cookbook = new Cookbook();
            // Add hardcoded recipes to the cookbook
            cookbook.initializeDefaultRecipes();
        }
        return new HashSet<>(cookbook.recipeList.values());
    }

    /**
     * Handles the event to view the cookbook menu.
     */
    public void onViewCookbookMenu(){
        if (listener != null) {
            listener.onViewCookbookMenu();
        }
    }

    /**
     * Handles the click event on a recipe in the list.
     *
     * @param recipe The clicked recipe.
     */
    @Override
    public void onRecipeClick(Recipe recipe) {
        if (listener != null) {
            listener.onRecipeClick(recipe);
        }
    }

    /**
     * Navigates to the Add Recipe screen.
     */
    public void navigateToAddRecipe() {
        if (listener != null) {
            listener.onNavigateToAddRecipe();
        }
    }

    /**
     * Updates the cookbook when recipes are loaded and refreshes the RecyclerView.
     *
     * @param cookbook The updated cookbook containing recipes.
     */
    @Override
    public void onCookbookRecipesLoaded(Cookbook cookbook) {
        this.cookbook = cookbook;
        if (recipeAdapter != null) {
            recipeAdapter.updateRecipes(cookbook);  // Update adapter's dataset
        }
    }

    /**
     * Sets up the RecyclerView with the recipe adapter and layout manager.
     */
    public void setupRecyclerView() {
        recyclerView = binding.recyclerViewRecipes;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recipeAdapter = new RecipeAdapter(cookbook, getContext(), this);
        recyclerView.setAdapter(recipeAdapter);
    }
}