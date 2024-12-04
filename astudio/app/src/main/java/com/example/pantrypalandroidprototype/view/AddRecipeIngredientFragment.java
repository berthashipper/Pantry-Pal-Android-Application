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
import com.example.pantrypalandroidprototype.databinding.FragmentAddRecipeIngredientBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;

/**
 * Fragment for adding a new ingredient to a recipe.
 * Implements the functionality for input validation and communicates with the parent activity through a listener.
 */
public class AddRecipeIngredientFragment extends Fragment {

    /** Binding object for accessing the views in the fragment's layout. */
    private FragmentAddRecipeIngredientBinding binding;

    /** Listener for handling interactions with this fragment. */
    private IAddRecipeIngredientView.Listener listener;

    /**
     * Creates a new instance of the fragment with a specified listener.
     *
     * @param listener The listener to handle interactions from this fragment.
     * @return A new instance of AddRecipeIngredientFragment.
     */
    public static AddRecipeIngredientFragment newInstance(IAddRecipeIngredientView.Listener listener) {
        AddRecipeIngredientFragment fragment = new AddRecipeIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater  The LayoutInflater object used to inflate views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root view of the inflated fragment layout.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddRecipeIngredientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Called immediately after the view hierarchy is created.
     * Sets up button click listeners and configures input fields.
     *
     * @param view The view returned by {@link #onCreateView}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupQuantityField();

        binding.addButton.setOnClickListener(v -> onAddButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    /**
     * Handles the "Add Ingredient" button click event.
     * Validates input and notifies the listener to add the new ingredient.
     */
    private void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String quantityInput = binding.itemQuantityText.getText().toString().trim();
        String unit = binding.itemUnitText.getText().toString().trim();

        if (name.isEmpty() || quantityInput.isEmpty()) {
            showError("Please fill out both fields.");
            return;
        }


        double newQty = Double.parseDouble(quantityInput);

        Set<Ingredient.dietary_tags> dietaryTags = new HashSet<>();
        // Check if dietary tags are selected and add to set
        if (binding.veganCheckbox.isChecked()) dietaryTags.add(Ingredient.dietary_tags.VEGAN);
        if (binding.kosherCheckbox.isChecked()) dietaryTags.add(Ingredient.dietary_tags.KOSHER);
        if (binding.glutenFreeCheckbox.isChecked()) dietaryTags.add(Ingredient.dietary_tags.GLUTEN_FREE);
        listener.onAddRecipeIngredient(name, newQty, unit, dietaryTags);
        showIngredientAddedMessage(name);
        clearInputs();
    }

    /**
     * Handles the "Done" button click event.
     * Notifies the listener that the ingredient adding process is complete.
     */
    private void onDoneButtonClicked() {
        if (listener != null) {
            listener.onAddRecipeDone();
        }
        Toast.makeText(getContext(), "Returning to Recipe Details", Toast.LENGTH_LONG).show();
    }

    /**
     * Configures the quantity input field to accept only valid numeric values.
     */
    private void setupQuantityField() {
        binding.itemQuantityText.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(10),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("") || source.toString().matches("[0-9]*\\.?[0-9]*")) {
                            return null; // Accept valid numbers
                        } else {
                            return ""; // Reject invalid input
                        }
                    }
                }
        });
    }

    /**
     * Clears the input fields for the ingredient name and quantity.
     */
    private void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQuantityText.setText("");
        binding.itemUnitText.setText("");
    }

    /**
     * Displays a message indicating that the ingredient was successfully added.
     *
     * @param name The name of the added ingredient.
     */
    private void showIngredientAddedMessage(String name) {
        Toast.makeText(getContext(), "Successfully added " + name, Toast.LENGTH_LONG).show();
    }

    /**
     * Displays a message with an error.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}