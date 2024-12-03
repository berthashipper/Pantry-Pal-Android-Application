package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.controller.ControllerActivity;
import com.example.pantrypalandroidprototype.databinding.FragmentRecipeListBinding;
import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

import java.util.Set;

/**
 * {@code RecipeFragment} is a {@link Fragment} responsible for displaying a list of recipes from a {@link Cookbook}.
 * It provides functionality for navigating to detailed views of recipes and handling updates to the cookbook's content.
 * It uses a {@link RecyclerView} to display the list of recipes and allows users to view individual recipe details.
 */
public class RecipeFragment extends Fragment {

    private static final String ARG_COOKBOOK = "cookbook";
    Cookbook cookbook;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    FragmentRecipeListBinding binding;

    /**
     * Creates a new instance of {@code RecipeFragment} with the specified {@link Cookbook}.
     *
     * @param cookbook The cookbook containing the list of recipes to be displayed in the fragment.
     * @return A new instance of {@code RecipeFragment}.
     */
    public static RecipeFragment newInstance(Cookbook cookbook) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COOKBOOK, cookbook);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is being created. Retrieves the cookbook argument passed to the fragment.
     *
     * @param savedInstanceState A bundle containing the fragment's previously saved state, if available.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cookbook = (Cookbook) getArguments().getSerializable(ARG_COOKBOOK);
        }
    }

    /**
     * Inflates the fragment's layout and sets up the {@link RecyclerView} to display the recipes.
     * Also, it initializes the {@link RecipeAdapter} with the recipes from the cookbook.
     *
     * @param inflater The LayoutInflater used to inflate the view.
     * @param container The parent view group that the fragment's UI will be attached to.
     * @param savedInstanceState A bundle containing the fragment's previously saved state, if available.
     * @return The root view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false);

        // Set up the RecyclerView
        binding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeAdapter = new RecipeAdapter(cookbook, getContext(),
                recipe -> {
                    // Navigate to RecipeDetailFragment on recipe click
                    RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstance(recipe);
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, detailFragment)
                            .addToBackStack(null)
                            .commit();
                },
                recipe -> {
                    // Handle recipe deletion
                    Snackbar.make(binding.getRoot(), "Recipe deleted", Snackbar.LENGTH_SHORT).show();
                    cookbook.removeRecipe(recipe);
                    recipeAdapter.removeRecipe(recipe);
                });
        binding.recipeRecyclerView.setAdapter(recipeAdapter);

        // Show a message if the cookbook is empty
        if (cookbook.recipeList.isEmpty()) {
            showNoRecipesMessage();
        }

        return binding.getRoot();
    }

    public void setRecipeClickListener(RecipeAdapter.OnRecipeClickListener listener) {
        recipeAdapter.setOnRecipeClickListener(listener);
    }

    /**
     * Updates the displayed list of recipes with a new {@link Cookbook}.
     *
     * @param newCookbook The new cookbook containing the updated list of recipes.
     */
    public void updateCookbook(Cookbook newCookbook) {
        if (binding != null) {
            recipeAdapter.updateRecipes(newCookbook);
        }
    }

    /**
     * Displays a message when no recipes are found in the cookbook.
     */
    public void showNoRecipesMessage() {
        View rootView = getView();
        if (rootView != null) {
            Snackbar.make(rootView, "No matching recipes found.", Snackbar.LENGTH_LONG).show();
        } else {
            Log.e("RecipeFragment", "Unable to show Snackbar: View is null");
        }
    }

    /**
     * Handles the event when a recipe is selected. Notifies the activity to handle the recipe click.
     *
     * @param recipe The recipe that was selected.
     */
    private void onRecipeSelected(Recipe recipe) {
        if (getActivity() instanceof ControllerActivity) {
            ((ControllerActivity) getActivity()).onRecipeClick(recipe);
        }
    }

    /**
     * Updates the recipe list in the adapter with a set of new recipes.
     *
     * @param recipes A set of {@link Recipe} objects to be displayed.
     */
    // Method to update the recipes list in the adapter
    public void updateRecipes(Set<Recipe> recipes) {
        // Convert the Set<Recipe> to a Cookbook, if necessary
        Cookbook newCookbook = new Cookbook();
        for (Recipe recipe : recipes) {
            newCookbook.addRecipe(recipe);
        }

        // Update the adapter with the new cookbook
        recipeAdapter.updateRecipes(newCookbook);
    }

    /**
     * Displays a message when a recipe has been deleted.
     *
     * @param recipeName The name of the deleted recipe.
     */
    public void showRecipeDeletedMessage(String recipeName) {
        Toast.makeText(getContext(), "Recipe '" + recipeName + "' deleted.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a message when a recipe cannot be found.
     *
     * @param recipeName The name of the recipe that could not be found.
     */
    public void showRecipeNotFoundError(String recipeName) {
        Toast.makeText(getContext(), "Recipe '" + recipeName + "' not found.", Toast.LENGTH_SHORT).show();
    }
}