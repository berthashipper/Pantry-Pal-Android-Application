package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentEditItemsBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.google.android.material.snackbar.Snackbar;

/**
 * Fragment for editing an ingredient's details, such as quantity, in the user's pantry.

 * This fragment allows the user to update the quantity of an existing ingredient and notifies
 * a listener about the changes. It also provides feedback through Snackbar messages for
 * validation errors and update statuses.
 */
public class EditIngredientFragment extends Fragment implements IEditIngredientView {
    static final String ARG_INGREDIENT = "ingredient";
    Ingredient ingredient;
    FragmentEditItemsBinding binding;
    Listener listener;

    /**
     * Creates a new instance of the fragment with the specified listener and ingredient to edit.
     *
     * @param listener   The {@link Listener} to handle user actions.
     * @param ingredient The {@link Ingredient} to be edited.
     * @return A new instance of {@code EditIngredientFragment}.
     */
    public static EditIngredientFragment newInstance(Listener listener, Ingredient ingredient) {
        EditIngredientFragment fragment = new EditIngredientFragment();
        fragment.listener = listener;
        Bundle args = new Bundle();
        args.putSerializable(ARG_INGREDIENT, ingredient);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Inflates the fragment's layout and initializes the ingredient details if provided.
     *
     * @param inflater           The {@link LayoutInflater} object for inflating views.
     * @param container          The parent view that this fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     * @return The root view of the fragment's layout.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditItemsBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            ingredient = (Ingredient) getArguments().getSerializable(ARG_INGREDIENT);
            Log.d("EditIngredientFragment", "Editing ingredient: " + ingredient.getName());
        }
        setupUI();
        return binding.getRoot();
    }

    /**
     * Initializes the fragment's view elements and sets up event listeners.
     *
     * @param view               The fragment's root view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            ingredient = (Ingredient) getArguments().getSerializable(ARG_INGREDIENT);
        }

        binding.editButton.setOnClickListener(v -> onEditButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    /**
     * Handles the "Edit" button click event, validating input and notifying the listener if valid.
     */
    public void onEditButtonClicked() {
        String quantityInput = binding.itemQuantityText.getText().toString().trim();

        if (quantityInput.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Invalid quantity.", Snackbar.LENGTH_LONG).show();
            return;
        }

        double newQty = Double.parseDouble(quantityInput);

        if (listener != null) {
            // Convert the ingredient name to lowercase for a case-insensitive check
            listener.onEditIngredient(this, ingredient.getName().toLowerCase(), newQty);
        }

        clearInputs();
    }

    /**
     * Displays a success message after updating an ingredient.
     *
     * @param name The name of the updated ingredient.
     */
    public void showIngredientUpdateMessage(String name) {
        Snackbar.make(binding.getRoot(), "Updated quantity " + name, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Displays an error message if the specified ingredient is not found.
     *
     * @param name The name of the missing ingredient.
     */
    public void showIngredientNotFoundError(String name) {
        Snackbar.make(binding.getRoot(), name + " does not exist in your pantry", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Handles the "Done" button click event, notifying the listener and displaying a message.
     */
    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onEditDone();
        }
        Snackbar.make(binding.getRoot(), "Returning to Pantry", Snackbar.LENGTH_LONG).show();
    }


    /**
     * Restricts the quantity input to only accept numeric values.
     */
    public void setupQuantityField() {
        binding.itemQuantityText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals("") || source.toString().matches("[0-9]*\\.?[0-9]*")) {
                    return null;  // Accept valid numbers
                } else {
                    return "";  // Reject non-numeric input
                }
            }
        }});
    }

    /**
     * Initializes the UI with the ingredient's current details and sets up input validation.
     */
    public void setupUI() {
        if (ingredient != null) {
            // Set the ingredient name as read-only
            binding.itemNameText.setText(ingredient.getName());
            binding.itemNameText.setFocusable(false);
            binding.itemNameText.setClickable(false);
            binding.itemNameText.setEnabled(false);

            // Populate the quantity field with the current value
            binding.itemQuantityText.setText(String.valueOf(ingredient.getQuantity()));
        }
        setupQuantityField();
    }

    /**
     * Clears the input fields for name and quantity.
     */
    public void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQuantityText.setText("");
    }

    /**
     * Displays a generic error message using a Snackbar.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}