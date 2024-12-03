package com.example.pantrypalandroidprototype.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.FragmentScaleRecipeBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;

public class ScaleRecipeFragment extends Fragment implements IScaleRecipeView.Listener {
    FragmentScaleRecipeBinding binding;
    IScaleRecipeView.Listener listener;
    Recipe recipe;

    public static ScaleRecipeFragment newInstance(Recipe recipe, IScaleRecipeView.Listener listener) {
        ScaleRecipeFragment fragment = new ScaleRecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipe", recipe);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScaleRecipeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.recipe = (Recipe) getArguments().getSerializable("recipe");
        }

        if (recipe == null) {
            Log.e("ScaleRecipeFragment", "Recipe object is null");
            Snackbar.make(view, "Error: Recipe data is missing", Snackbar.LENGTH_LONG).show();
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
                    Snackbar.make(getView(), "Scale factor must be greater than 0", Snackbar.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
                Snackbar.make(getView(), "Invalid scale factor", Snackbar.LENGTH_LONG).show();
            }
        });

        binding.backToRecipeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBackToRecipe();  // Notify the controller to navigate back
            }
        });
    }

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

    public void onScaleButtonClicked() {
        try {
            double scaleFactor = Double.parseDouble(binding.scaleFactor.getText().toString());
            if (listener != null) {
                listener.onScaleRecipe(scaleFactor);  // Pass the scale factor to the controller
            }
        } catch (NumberFormatException e) {
            Snackbar.make(getView(), "Invalid scale factor", Snackbar.LENGTH_LONG).show();
        }
    }


    public void showScaleError() {
        binding.errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onScaleRecipe(double scaleFactor) {

    }

    @Override
    public void onRecipeScaled(Recipe scaledRecipe) {

    }

    @Override
    public void onBackToRecipe() {

    }
}
