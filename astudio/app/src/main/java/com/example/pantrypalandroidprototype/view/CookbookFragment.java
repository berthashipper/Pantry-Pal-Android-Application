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
import com.example.pantrypalandroidprototype.databinding.FragmentCookbookBinding;
import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;

/**
 * Fragment for managing and displaying a user's cookbook.
 * Provides functionalities such as viewing, adding, searching, and deleting recipes in the cookbook.
 * It also allows interaction with recipes via a {@link RecyclerView} and supports navigation to other fragments.
 */
public class CookbookFragment extends Fragment implements ICookbookView, RecipeAdapter.OnRecipeClickListener {
    FragmentCookbookBinding binding;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    Cookbook cookbook;
    Listener listener;

    /**
     * Creates a new instance of the fragment with a specified listener and cookbook.
     *
     * @param listener A {@link ICookbookView.Listener} to handle cookbook-related actions.
     * @param cookbook The initial cookbook to manage. If null, a default cookbook is created.
     * @return A new instance of {@code CookbookFragment}.
     */
    public static CookbookFragment newInstance(ICookbookView.Listener listener, Cookbook cookbook) {
        CookbookFragment fragment = new CookbookFragment();
        fragment.listener = listener;
        fragment.cookbook = (cookbook != null) ? cookbook : new Cookbook();
        return fragment;
    }

    /**
     * Inflates the fragment's layout and sets up the user interface.
     *
     * @param inflater           The {@link LayoutInflater} used to inflate the layout.
     * @param container          The parent view that this fragment's UI will attach to.
     * @param savedInstanceState Saved state information for restoring the fragment.
     * @return The root {@link View} of the fragment.
     */
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

        setupRecyclerView();
        // Add click listener for the Search Recipes button
        binding.searchRecipesButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSearchRecipesMenu(); // Navigate to fragment
            }
        });

        // Add click listener for the Filter Recipes button
        binding.filterRecipesButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFilterRecipesMenu(); // Navigate to fragment
            }
        });

        return rootView;
    }

    /**
     * Called after the fragment's view has been created.
     *
     * @param view               The {@link View} of the fragment.
     * @param savedInstanceState Saved state information for restoring the fragment.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Callback when a recipe is created. Override as needed.
     *
     * @param recipe The newly created {@link Recipe}.
     */
    @Override
    public void onRecipeCreated(Recipe recipe) {

    }

    /**
     * Opens the search menu for recipes. Displays a dialog for search functionality.
     */
    @Override
    public void onSearchRecipesMenu() {
        showSearchDialog();
    }

    /**
     * Displays a search dialog to filter or find recipes in the cookbook.
     */
    private void showSearchDialog() {

    }

    /**
     * Retrieves all recipes currently in the cookbook.
     * <p>
     * Ensures that a default cookbook is initialized if none exists.
     * </p>
     *
     * @return A {@link Set} of {@link Recipe} objects from the cookbook.
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
     * Handles viewing the cookbook menu. Notifies the listener if set.
     */
    public void onViewCookbookMenu(){
        if (listener != null) {
            listener.onViewCookbookMenu();
        }
    }

    /**
     * Handles the click event for a recipe item.
     *
     * @param recipe The clicked {@link Recipe}.
     */
    @Override
    public void onRecipeClick(Recipe recipe) {
        if (listener != null) {
            listener.onRecipeClick(recipe);
        }
    }

    /**
     * Navigates to the fragment for adding a new recipe.
     */
    public void navigateToAddRecipe() {
        if (listener != null) {
            listener.onNavigateToAddRecipe();
        }
    }

    /**
     * Updates the fragment's cookbook data when recipes are loaded.
     *
     * @param cookbook The updated {@link Cookbook}.
     */
    @Override
    public void onCookbookRecipesLoaded(Cookbook cookbook) {
        this.cookbook = cookbook;
        if (recipeAdapter != null) {
            recipeAdapter.updateRecipes(cookbook);  // Update adapter's dataset
        }
    }

    /**
     * Sets up the {@link RecyclerView} for displaying recipes.
     * Configures the adapter to handle recipe clicks and deletions, and updates the cookbook upon changes.
     */
    public void setupRecyclerView() {
        recyclerView = binding.recyclerViewRecipes;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recipeAdapter = new RecipeAdapter(cookbook, getContext(),
                new RecipeAdapter.OnRecipeClickListener() {
                    @Override
                    public void onRecipeClick(Recipe recipe) {
                        if (listener != null) {
                            listener.onRecipeClick(recipe);
                        }
                    }
                },
                recipe -> {
                    // Remove recipe from the cookbook
                    cookbook.removeRecipe(recipe);

                    // Notify adapter about the change
                    recipeAdapter.notifyDataSetChanged();

                    // Persist the updated cookbook
                    if (listener != null) {
                        listener.onCookbookUpdated(cookbook);
                    }

                    Snackbar.make(binding.getRoot(), "Recipe deleted", Snackbar.LENGTH_SHORT).show();
                }
        );
        recyclerView.setAdapter(recipeAdapter);
    }
}