package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pantrypalandroidprototype.databinding.FragmentAddRecipeBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.example.pantrypalandroidprototype.model.RecipeBuilder;
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

        double quantity = Double.parseDouble(quantityString);
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

    private void onDoneButtonClicked() {
        String recipeName = binding.recipeNameEditText.getText().toString().trim();
        String description = binding.descriptionEditText.getText().toString().trim();

        if (recipeName.isEmpty()) {
            Toast.makeText(getContext(), "Please provide a recipe name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save recipe using the RecipeBuilder
        recipeBuilder.setName(recipeName);
        recipeBuilder.setDescription(description);
        listener.onRecipeCreated(recipeBuilder.build());

        // Notify user
        Toast.makeText(getContext(), "Recipe saved successfully", Toast.LENGTH_SHORT).show();
    }

    private void clearIngredientFields() {
        binding.ingredientNameEditText.setText("");
        binding.ingredientQuantityEditText.setText("");
        binding.ingredientUnitEditText.setText("");
    }

    private void clearInstructionField() {
        binding.instructionEditText.setText("");
    }

}
