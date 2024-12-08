package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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
import com.google.android.material.snackbar.Snackbar;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class AddRecipeFragment extends Fragment implements IAddRecipeView {

    FragmentAddRecipeBinding binding;
    Listener listener;
    RecipeBuilder recipeBuilder = new RecipeBuilder();

    public static AddRecipeFragment newInstance(Listener listener) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false);
        setupInputFields();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addIngredientButton.setOnClickListener(v -> onAddIngredientButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
        binding.addInstructionButton.setOnClickListener(v -> onAddInstructionButtonClicked());
        binding.backToCookbookIcon.setOnClickListener(v -> onBackToCookbookIconClicked());
    }

    public void onAddIngredientButtonClicked() {
        String name = binding.ingredientNameEditText.getText().toString().trim();
        String quantityString = binding.ingredientQuantityEditText.getText().toString().trim();
        String unit = binding.ingredientUnitEditText.getText().toString().trim();

        if (name.isEmpty() || quantityString.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
            return;
        }

        double quantity = Double.parseDouble(quantityString);

        /*Set<String> dietaryTags = new HashSet<>();
        // Check if dietary tags are selected and add to set
        if (binding.veganCheckbox.isChecked()) dietaryTags.add("VEGAN");
        if (binding.kosherCheckbox.isChecked()) dietaryTags.add("KOSHER");
        if (binding.glutenFreeCheckbox.isChecked()) dietaryTags.add("GLUTEN_FREE");*/

        // Add the ingredient to the RecipeBuilder
        recipeBuilder.addIngredient(name, (int) quantity, unit, null);

        // Notify user
        Snackbar.make(binding.getRoot(), "Ingredient added: " + name, Snackbar.LENGTH_SHORT).show();
        clearIngredientFields();
    }

    public void onAddInstructionButtonClicked() {
        String instruction = binding.instructionEditText.getText().toString().trim();

        if (instruction.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Please enter a valid instruction", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Add instruction to RecipeBuilder
        recipeBuilder.addInstruction(instruction);

        // Notify user
        Snackbar.make(binding.getRoot(), "Instruction added", Snackbar.LENGTH_SHORT).show();
        clearInstructionField();
    }

    public void onDoneButtonClicked() {
        String recipeName = binding.recipeNameEditText.getText().toString().trim();
        String description = binding.descriptionEditText.getText().toString().trim();
        String cookTimeString = binding.cookTimeEditText.getText().toString().trim();
        String servingSizeString = binding.servingSizeEditText.getText().toString().trim();

        if (recipeName.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Please provide a recipe name", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Validate and set cook time
        Duration cookTime = Duration.ofMinutes(0);  // Default to 0 minutes if not provided
        if (!cookTimeString.isEmpty()) {
            try {
                long cookTimeInMinutes = Long.parseLong(cookTimeString);
                cookTime = Duration.ofMinutes(cookTimeInMinutes);
            } catch (NumberFormatException e) {
                Snackbar.make(binding.getRoot(), "Please enter a valid cook time", Snackbar.LENGTH_SHORT).show();
                return; // Return if invalid cook time entered
            }
        }

        // Validate and set serving size
        int servingSize = 0;  // Default to 0 servings if not provided
        if (!servingSizeString.isEmpty()) {
            try {
                servingSize = Integer.parseInt(servingSizeString);
            } catch (NumberFormatException e) {
                Snackbar.make(binding.getRoot(), "Please enter a valid serving size", Snackbar.LENGTH_SHORT).show();
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

    public void onBackToCookbookIconClicked() {
        // Notify the listener or navigate back to the cookbook
        if (listener != null) {
            listener.onNavigateToCookbook();
        }
    }

    public void clearIngredientFields() {
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
            listener.onRecipeCreated(newRecipe);
        }
    }

    public void setupInputFields() {
        // Restrict quantity field to numbers only
        binding.ingredientQuantityEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("") || source.toString().matches("[0-9]*\\.?[0-9]*")) {
                            return null;  // Accept valid numbers (whole numbers or decimals)
                        } else {
                            return "";  // Reject non-numeric input
                        }
                    }
                }});

        // Restrict cooktime field to numbers only (for whole numbers or decimals)
        binding.cookTimeEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("") || source.toString().matches("[0-9]*\\.?[0-9]*")) {
                            return null;  // Accept valid numbers (whole numbers or decimals)
                        } else {
                            return "";  // Reject non-numeric input
                        }
                    }
                }});

        // Restrict serving size field to numbers only (for whole numbers)
        binding.servingSizeEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("") || source.toString().matches("[0-9]+")) {
                            return null;  // Accept valid whole numbers only
                        } else {
                            return "";  // Reject non-numeric input
                        }
                    }
                }});
    }
}
