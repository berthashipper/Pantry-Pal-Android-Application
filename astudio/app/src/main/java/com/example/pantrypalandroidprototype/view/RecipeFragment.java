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
import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.example.pantrypalandroidprototype.model.RecipeAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecipeFragment extends Fragment {

    private static final String ARG_COOKBOOK = "cookbook";
    private Cookbook cookbook;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;

    public static RecipeFragment newInstance(Cookbook cookbook) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COOKBOOK, cookbook);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cookbook = (Cookbook) getArguments().getSerializable(ARG_COOKBOOK);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recipe_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecipeAdapter adapter = new RecipeAdapter(cookbook, getContext(), recipe -> {
            // Navigate to RecipeDetailFragment on recipe click
            RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstance(recipe);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.setAdapter(adapter);

        // Show a message if the cookbook is empty
        if (cookbook.recipeList.isEmpty()) {
            showNoRecipesMessage();
        }

        return view;
    }

    public void updateCookbook(Cookbook newCookbook) {
        View rootView = getView();
        if (rootView != null) {
            RecyclerView recyclerView = rootView.findViewById(R.id.recipe_recycler_view);
            RecipeAdapter adapter = (RecipeAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                adapter.updateRecipes(newCookbook);
            }
        }
    }

    public void showNoRecipesMessage() {
        View rootView = getView();
        if (rootView != null) {
            Snackbar.make(rootView, "No matching recipes found.", Snackbar.LENGTH_LONG).show();
        } else {
            Log.e("RecipeFragment", "Unable to show Snackbar: View is null");
        }
    }

    private void onRecipeSelected(Recipe recipe) {
        if (getActivity() instanceof ControllerActivity) {
            ((ControllerActivity) getActivity()).onRecipeClick(recipe);
        }
    }

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

    public void showRecipeDeletedMessage(String recipeName) {
        Toast.makeText(getContext(), "Recipe '" + recipeName + "' deleted.", Toast.LENGTH_SHORT).show();
    }

    public void showRecipeNotFoundError(String recipeName) {
        Toast.makeText(getContext(), "Recipe '" + recipeName + "' not found.", Toast.LENGTH_SHORT).show();
    }
}


