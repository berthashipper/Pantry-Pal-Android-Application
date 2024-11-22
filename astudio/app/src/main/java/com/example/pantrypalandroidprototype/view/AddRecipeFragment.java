package com.example.pantrypalandroidprototype.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.controller.ControllerActivity;
import com.example.pantrypalandroidprototype.databinding.FragmentAddRecipeBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.example.pantrypalandroidprototype.model.RecipeBuilder;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class AddRecipeFragment extends Fragment implements IAddRecipeView {

    private FragmentAddRecipeBinding binding;
    private Listener listener;
    private RecipeBuilder recipeBuilder = new RecipeBuilder();

    public static AddRecipeFragment newInstance(Listener listener) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up listeners
        binding.addIngredientButton.setOnClickListener(v -> onAddIngredientButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
        binding.addInstructionButton.setOnClickListener(v -> onAddInstructionButtonClicked());
    }

    private void onAddIngredientButtonClicked() {
        String name = binding.ingredientNameEditText.getText().toString().trim();
        String quantityString = binding.ingredientQuantityEditText.getText().toString().trim();
        String unit = binding.ingredientUnitEditText.getText().toString().trim();

        if (name.isEmpty() || quantityString.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate quantity input
        double quantity = -1;
        while (quantity == -1) {
            try {
                quantity = Double.parseDouble(quantityString);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter a valid quantity (number)", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Set<Ingredient.dietary_tags> dietaryTags = new HashSet<>();
        // Check if dietary tags are selected and add to set
        if (binding.veganCheckbox.isChecked()) dietaryTags.add(Ingredient.dietary_tags.VEGAN);
        if (binding.kosherCheckbox.isChecked()) dietaryTags.add(Ingredient.dietary_tags.KOSHER);
        if (binding.glutenFreeCheckbox.isChecked()) dietaryTags.add(Ingredient.dietary_tags.GLUTEN_FREE);

        // Add the ingredient to the RecipeBuilder
        recipeBuilder.addIngredient(name, (int) quantity, unit, dietaryTags);

        // Notify user
        Toast.makeText(getContext(), "Ingredient added: " + name, Toast.LENGTH_SHORT).show();
        clearIngredientFields();
    }

    private void onAddInstructionButtonClicked() {
        String instruction = binding.instructionEditText.getText().toString().trim();

        if (instruction.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a valid instruction", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add instruction to RecipeBuilder
        recipeBuilder.addInstruction(instruction);

        // Notify user
        Toast.makeText(getContext(), "Instruction added", Toast.LENGTH_SHORT).show();
        clearInstructionField();
    }

    public void onDoneButtonClicked() {
        String recipeName = binding.recipeNameEditText.getText().toString().trim();
        String description = binding.descriptionEditText.getText().toString().trim();
        String cookTimeString = binding.cookTimeEditText.getText().toString().trim();
        String servingSizeString = binding.servingSizeEditText.getText().toString().trim();

        if (recipeName.isEmpty()) {
            Toast.makeText(getContext(), "Please provide a recipe name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate and set cook time
        Duration cookTime = Duration.ofMinutes(0);  // Default to 0 minutes if not provided
        if (!cookTimeString.isEmpty()) {
            try {
                long cookTimeInMinutes = Long.parseLong(cookTimeString);
                cookTime = Duration.ofMinutes(cookTimeInMinutes);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter a valid cook time", Toast.LENGTH_SHORT).show();
                return; // Return if invalid cook time entered
            }
        }

        // Validate and set serving size
        int servingSize = 0;  // Default to 0 servings if not provided
        if (!servingSizeString.isEmpty()) {
            try {
                servingSize = Integer.parseInt(servingSizeString);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter a valid serving size", Toast.LENGTH_SHORT).show();
                return; // Return if invalid serving size entered
            }
        }

        // Save recipe using the RecipeBuilder
        recipeBuilder.setName(recipeName);
        recipeBuilder.setDescription(description);
        recipeBuilder.setCookTime(cookTime);
        recipeBuilder.setServingSize(servingSize);

        Recipe recipe = recipeBuilder.build();

        // Notify the listener to display RecipeDetailFragment
        if (listener != null) {
            listener.onRecipeCreated(recipe);
        }
    }

    private void clearIngredientFields() {
        binding.ingredientNameEditText.setText("");
        binding.ingredientQuantityEditText.setText("");
        binding.ingredientUnitEditText.setText("");
    }

    public void clearInstructionField() {
        binding.instructionEditText.setText("");
    }

    @Override
    public void onSaveRecipe() {
        Recipe newRecipe = recipeBuilder.build();
        if (listener != null) {
            listener.onRecipeCreated(newRecipe); // Notify the listener (ControllerActivity)
        }
    }
}
