package com.example.pantrypalandroidprototype.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.controller.ControllerActivity;
import com.example.pantrypalandroidprototype.databinding.FragmentRecipeDetailBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

import java.time.Duration;

public class RecipeDetailFragment extends Fragment implements IRecipeDetailView {
    static final String ARG_RECIPE = "recipe";
    Recipe recipe;
    Listener listener;
    FragmentRecipeDetailBinding binding;
    ControllerActivity controller;
    static final int REQUEST_EDIT_COOK_TIME = 1;
    static final int REQUEST_EDIT_SERVING_SIZE = 2;


    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ControllerActivity) {
            controller = (ControllerActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " must be an instance of ControllerActivity");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the binding object
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false);

        binding.recipeName.setText(recipe.getRecipeName());
        binding.recipeDescription.setText(recipe.getRecipeDescription());
        binding.recipeCookTime.setText("Cook Time: " + formatCookTime(recipe.cookTime));
        binding.recipeServingSize.setText("Serves: " + recipe.servingSize);

        // Add ingredients to the layout
        for (Ingredient ingredient : recipe.getIngredients()) {
            TextView ingredientView = new TextView(getContext());
            ingredientView.setText(ingredient.getQuantity() + " " + ingredient.getUnit() + " of " + ingredient.getName());
            binding.ingredientsLayout.addView(ingredientView);
        }

        // Set the recipe instructions
        binding.recipeInstructions.setText(recipe.instructions);

        // Edit Cook Time
        binding.editCookTime.setOnClickListener(v -> {
            showEditDialog(REQUEST_EDIT_COOK_TIME);
        });

        // Edit Serving Size
        binding.editServingSize.setOnClickListener(v -> {
            showEditDialog(REQUEST_EDIT_SERVING_SIZE);
        });

        //Set up the "Edit" button to navigate to AddRecipeIngredientFragment
        binding.editRecipeIngredient.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditRecipeIngredients();
            }
        });

        //Set up the "Add" button to navigate to AddRecipeIngredientFragment
        binding.addRecipeIngredient.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddRecipeIngredients();
            }
        });

        // Set up the "Delete" button to navigate back to DeleteRecipeIngredientFragment
        binding.deleteRecipeIngredient.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteRecipeIngredients();  // Notify listener when Delete is pressed
            }
        });

        // Set up the "Edit" button to navigate back to EditInstructionFragment
        binding.editInstruction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditInstructions(); // Notify listener when Edit is pressed
            }
        });

        // Set up the "Done" button to navigate back to CookbookFragment
        binding.doneButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDoneViewingRecipe();  // Notify listener when Done is pressed
            }
        });

        // Set up the "Scale" button to navigate to ScaleRecipeFragment
        binding.scaleButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onScaleRecipeMenu();  // Notify listener when Scale is pressed
            }
        });

        return binding.getRoot();
    }

    public String formatCookTime(Duration cookTime) {
        if (cookTime != null) {
            long minutes = cookTime.toMinutes(); // Convert Duration to minutes
            return minutes + " minutes";
        }
        return "Not specified";
    }

    public void showEditDialog(int requestCode) {
        // Show appropriate edit dialog based on the request code
        EditDialogFragment dialog = EditDialogFragment.newInstance(requestCode, recipe);
        dialog.setTargetFragment(this, requestCode);
        dialog.show(getFragmentManager(), "EditDialog");
    }


    @Override
    public void onDialogEditDone(int requestCode, String newValue) {
        if (requestCode == REQUEST_EDIT_COOK_TIME) {
            try {
                // Parse the input as an integer (representing minutes)
                long newCookTimeMinutes = Long.parseLong(newValue);
                Duration newCookTime = Duration.ofMinutes(newCookTimeMinutes);

                // Update the cook time via controller
                controller.setCookTime(newCookTime, recipe);

                // Update the UI
                binding.recipeCookTime.setText(newCookTimeMinutes + " mins");
                Snackbar.make(getView(), "Cook time updated to " + newCookTimeMinutes + " mins", Snackbar.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                // Handle invalid input (non-numeric)
                Snackbar.make(getView(), "Invalid input for cook time", Snackbar.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_EDIT_SERVING_SIZE) {
            controller.setServingSize(Integer.parseInt(newValue), recipe);
            binding.recipeServingSize.setText(newValue + " servings");
            Snackbar.make(getView(), "Yield updated to " + newValue, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void onBackToRecipe() {
        controller.onBackToRecipe(recipe);  // Delegate to controller
    }
}