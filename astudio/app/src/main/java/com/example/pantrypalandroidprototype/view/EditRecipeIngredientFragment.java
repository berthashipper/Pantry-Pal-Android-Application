package com.example.pantrypalandroidprototype.view;


import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentEditRecipeIngredientBinding;
import com.google.android.material.snackbar.Snackbar;


public class EditRecipeIngredientFragment extends Fragment implements IEditRecipeIngredientView {

    private FragmentEditRecipeIngredientBinding binding;
    private Listener listener;

    /**
     * Creates a new instance of the fragment with a listener.
     *
     * @param listener The listener to handle interactions from this fragment.
     * @return A new instance of the fragment.
     */
    public static EditRecipeIngredientFragment newInstance(Listener listener) {
        EditRecipeIngredientFragment fragment = new EditRecipeIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Called to create the view hierarchy of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditRecipeIngredientBinding.inflate(inflater, container, false);
        setupQuantityField();
        return binding.getRoot();
    }

    /**
     * Called after the fragment's view has been created.
     * Sets up button click listeners and other view-related logic.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editButton.setOnClickListener(v -> onEditButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());

        binding.backToRecipeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBackToRecipe();
            }
        });
    }

    /**
     * Handles the edit button click event.
     * Validates input and notifies the listener to update the ingredient's quantity.
     */
    private void onEditButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String quantityInput = binding.itemQuantityText.getText().toString().trim();

        if (quantityInput.isEmpty() || name.isEmpty()) {
            showError("Please fill out both fields.");
            return;
        }

        try {
            double newQty = Double.parseDouble(quantityInput);
            if (listener != null) {
                boolean ingredientExists = listener.isIngredientExists(name); // Check if ingredient exists
                if (!ingredientExists) {
                    showError(name + " does not exist in the recipe.");
                    return;
                }
                listener.onEditRecipeIngredient(name, newQty);
                showIngredientUpdateMessage(name);
                clearInputs();
            }
        } catch (NumberFormatException e) {
            showError("Invalid quantity format.");
        }
    }

    /**
     * Handles the Done button click event.
     * Notifies the listener that the editing process is complete.
     */
    private void onDoneButtonClicked() {
        if (listener != null) {
            listener.onEditRecipeDone();
        }
        //Snackbar.make(binding.getRoot(), "Returning to Recipe Details", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Sets up the quantity input field to only accept valid numeric values.
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
                            return ""; // Reject non-numeric input
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
    }

    /**
     * Displays a Snackbar message indicating a successful update for the ingredient.
     *
     * @param name The name of the updated ingredient.
     */
    public void showIngredientUpdateMessage(String name) {
        Snackbar.make(binding.getRoot(), "Updated quantity of " + name, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Displays a Snackbar message with an error.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}
