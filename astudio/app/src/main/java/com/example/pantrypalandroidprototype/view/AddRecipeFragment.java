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

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code AddRecipeFragment} class represents a fragment for creating and adding recipes.
 * It implements the {@link IAddRecipeView} interface and provides functionality to input recipe details,
 * ingredients, and instructions, while validating the input fields.
 */
public class AddRecipeFragment extends Fragment implements IAddRecipeView {
    /**
     * View binding for accessing the UI elements of the fragment.
     */
    FragmentAddRecipeBinding binding;
    /**
     * Listener for handling interactions with the fragment.
     */
    Listener listener;
    /**
     * Instance of {@link RecipeBuilder} for building and managing the recipe creation process.
     */
    RecipeBuilder recipeBuilder = new RecipeBuilder();

    /**
     * Creates a new instance of {@code AddRecipeFragment} with a specified listener.
     *
     * @param listener The listener to handle fragment interactions.
     * @return A new instance of {@code AddRecipeFragment}.
     */
    public static AddRecipeFragment newInstance(Listener listener) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        fragment.listener = listener;
        return fragment;
    }


    /**
     * Called to create the fragment's view hierarchy.
     *
     * @param inflater  The {@link LayoutInflater} object for inflating views.
     * @param container The parent view group for the fragment's UI.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     * @return The root view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false);
        setupInputFields();
        return binding.getRoot();
    }


    /**
     * Called immediately after the fragment's view has been created.
     *
     * @param view The fragment's root view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up listeners
        binding.addIngredientButton.setOnClickListener(v -> onAddIngredientButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
        binding.addInstructionButton.setOnClickListener(v -> onAddInstructionButtonClicked());
    }


    /**
     * Handles the "Add Ingredient" button click event.
     * Validates the ingredient input fields, creates an ingredient, and adds it to the recipe.
     */
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
//        while (quantity == -1) {
//            try {
                quantity = Double.parseDouble(quantityString);
//            } catch (NumberFormatException e) {
//                Toast.makeText(getContext(), "Please enter a valid quantity (number)", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }

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


    /**
     * Handles the "Add Instruction" button click event.
     * Validates the instruction input field and adds the instruction to the recipe.
     */
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

    /**
     * Handles the "Done" button click event.
     * Validates recipe details and notifies the listener to save the created recipe.
     */
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

    /**
     * Clears the ingredient input fields.
     */
    private void clearIngredientFields() {
        binding.ingredientNameEditText.setText("");
        binding.ingredientQuantityEditText.setText("");
        binding.ingredientUnitEditText.setText("");
    }

    /**
     * Clears the instruction input field.
     */
    public void clearInstructionField() {
        binding.instructionEditText.setText("");
    }

    /**
     * Saves the current recipe and notifies the listener.
     */
    @Override
    public void onSaveRecipe() {
        Recipe newRecipe = recipeBuilder.build();
        if (listener != null) {
            listener.onRecipeCreated(newRecipe);
        }
    }

    /**
     * Sets up input fields to restrict input to numeric values for quantity, cook time, and serving size.
     */
    private void setupInputFields() {
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
