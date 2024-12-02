package com.example.pantrypalandroidprototype.view;

import android.content.Context;
import android.os.Bundle;
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
import com.google.firebase.firestore.util.Listener;

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

        binding.recipeName.setText(recipe.getRecipeName());
        //binding.scaleFactor.setText("1.0"); // Default scale factor

        binding.scaleButton.setOnClickListener(v -> {
            try {
                double scaleFactor = Double.parseDouble(binding.scaleFactor.getText().toString());
                if (scaleFactor > 0) {
                    Recipe scaledRecipe = scaleRecipe(recipe, scaleFactor);
                    listener.onRecipeScaled(scaledRecipe);
                } else {
                    // Show error message for invalid scale factor
                    Toast.makeText(getContext(), "Scale factor must be greater than 0", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
                Toast.makeText(getContext(), "Invalid scale factor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Recipe scaleRecipe(Recipe originalRecipe, double scaleFactor) {
        Set<Ingredient> scaledIngredients = new HashSet<>();
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

        return new Recipe(
                originalRecipe.getRecipeName(),
                scaledIngredients,
                originalRecipe.getInstructions(),
                originalRecipe.getTags(),
                originalRecipe.getRecipeDescription(),
                originalRecipe.getCookTime(),
                originalRecipe.getServingSize(),
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
            Toast.makeText(getContext(), "Invalid scale factor", Toast.LENGTH_SHORT).show();
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
}
