package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentScaleRecipeBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;

/**
 * A fragment that allows users to scale a recipe's ingredients and serving size.
 * This fragment interacts with the controller through the {@link IScaleRecipeView.Listener} interface.
 */
public class ScaleRecipeFragment extends Fragment implements IScaleRecipeView.Listener {
    FragmentScaleRecipeBinding binding;
    IScaleRecipeView.Listener listener;
    Recipe recipe;

    /**
     * Creates a new instance of the {@link ScaleRecipeFragment} with the given recipe and listener.
     *
     * @param recipe  the recipe to be scaled
     * @param listener  the listener to handle scaling results
     * @return a new instance of {@link ScaleRecipeFragment}
     */
    public static ScaleRecipeFragment newInstance(Recipe recipe, IScaleRecipeView.Listener listener) {
        ScaleRecipeFragment fragment = new ScaleRecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipe", recipe);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Inflates the layout for the fragment and returns the root view.
     *
     * @param inflater the layout inflater
     * @param container the container view group
     * @param savedInstanceState the saved instance state
     * @return the root view of the fragment's layout
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScaleRecipeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Initializes the fragment by retrieving the recipe and setting up listeners.
     *
     * @param view the fragment's root view
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.recipe = (Recipe) getArguments().getSerializable("recipe");
        }

        if (recipe == null) {
            Log.e("ScaleRecipeFragment", "Recipe object is null");
            Snackbar.make(binding.getRoot(), "Error: Recipe data is missing", Snackbar.LENGTH_LONG).show();
            return;  // Prevent further execution
        }

        binding.recipeName.setText(recipe.getRecipeName());

        binding.scaleButton.setOnClickListener(v -> {
            try {
                double scaleFactor = Double.parseDouble(binding.scaleFactor.getText().toString());
                if (scaleFactor > 0) {
                    Recipe scaledRecipe = scaleRecipe(recipe, scaleFactor);
                    listener.onRecipeScaled(scaledRecipe);
                } else {
                    // Show error message for invalid scale factor
                    Snackbar.make(binding.getRoot(), "Scale factor must be greater than 0", Snackbar.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
                Snackbar.make(binding.getRoot(), "Invalid scale factor", Snackbar.LENGTH_LONG).show();
            }
        });

        binding.backToRecipeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBackToRecipe();  // Notify the controller to navigate back
            }
        });
    }

    /**
     * Scales the recipe ingredients and serving size based on the given scale factor.
     *
     * @param originalRecipe the recipe to scale
     * @param scaleFactor the factor by which to scale the recipe
     * @return the scaled recipe
     */
    public Recipe scaleRecipe(Recipe originalRecipe, double scaleFactor) {
        Set<Ingredient> scaledIngredients = new HashSet<>();
        // Scale each ingredient's quantity based on the scale factor
        for (Ingredient ingredient : originalRecipe.getIngredients()) {
            double newQuantity = ingredient.getQuantity() * scaleFactor;
            Ingredient scaledIngredient = new Ingredient(
                    ingredient.getName(),
                    newQuantity,
                    ingredient.getUnit(),
                    ingredient.getTags()
            );
            scaledIngredients.add(scaledIngredient);
        }

        // Scale the serving size
        int scaledServingSize = (int) (originalRecipe.getServingSize() * scaleFactor);

        return new Recipe(
                originalRecipe.getRecipeName(),
                scaledIngredients,
                originalRecipe.getInstructions(),
                originalRecipe.getTags(),
                originalRecipe.getRecipeDescription(),
                originalRecipe.getCookTime(),
                scaledServingSize,
                originalRecipe.getUrl()
        );
    }

    /**
     * Handles the scaling process when the scale button is clicked.
     * It parses the scale factor and passes it to the listener.
     */
    public void onScaleButtonClicked() {
        try {
            double scaleFactor = Double.parseDouble(binding.scaleFactor.getText().toString());
            if (listener != null) {
                listener.onScaleRecipe(scaleFactor);  // Pass the scale factor to the controller
            }
        } catch (NumberFormatException e) {
            Snackbar.make(binding.getRoot(), "Invalid scale factor", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Displays an error message when the scale factor is invalid.
     */
    public void showScaleError() {
        binding.errorMessage.setVisibility(View.VISIBLE);
    }

    /**
     * Callback method invoked when the recipe is scaled.
     * This is a part of the {@link IScaleRecipeView.Listener} interface.
     *
     * @param scaleFactor the scale factor used to scale the recipe
     */
    @Override
    public void onScaleRecipe(double scaleFactor) {

    }

    /**
     * Callback method invoked when the recipe has been successfully scaled.
     * This is a part of the {@link IScaleRecipeView.Listener} interface.
     *
     * @param scaledRecipe the scaled recipe object
     */
    @Override
    public void onRecipeScaled(Recipe scaledRecipe) {

    }

    /**
     * Callback method invoked when the user navigates back to the recipe.
     * This is a part of the {@link IScaleRecipeView.Listener} interface.
     */
    @Override
    public void onBackToRecipe() {

    }
}
