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
import com.example.pantrypalandroidprototype.model.RecipeAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;

public class CookbookFragment extends Fragment implements ICookbookView, RecipeAdapter.OnRecipeClickListener {

    FragmentCookbookBinding binding;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    Cookbook cookbook;
    Listener listener;

    public static CookbookFragment newInstance(ICookbookView.Listener listener, Cookbook cookbook) {
        CookbookFragment fragment = new CookbookFragment();
        fragment.listener = listener;
        fragment.cookbook = (cookbook != null) ? cookbook : new Cookbook();
        return fragment;
    }

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

    @Override
    public void onSearchRecipesMenu() {
        // Show a dialog or navigate to a search fragment
        showSearchDialog();
    }

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

    public Set<Recipe> getAllRecipes() {
        // Ensure the cookbook is not null
        if (cookbook == null) {
            cookbook = new Cookbook();
            // Add hardcoded recipes to the cookbook
            cookbook.initializeDefaultRecipes();
        }
        return new HashSet<>(cookbook.recipeList.values());
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
    public void onCookbookRecipesLoaded(Cookbook cookbook) {
        this.cookbook = cookbook;
        if (recipeAdapter != null) {
            recipeAdapter.updateRecipes(cookbook);  // Update adapter's dataset
        }
    }

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