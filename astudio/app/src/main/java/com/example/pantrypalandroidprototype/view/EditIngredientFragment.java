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

import com.example.pantrypalandroidprototype.databinding.FragmentEditItemsBinding;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.google.android.material.snackbar.Snackbar;

/**
 * The {@code EditIngredientFragment} class provides functionality for editing the quantity of an ingredient in the user's pantry.
 * It implements the {@link IEditIngredientView} interface and manages user interactions within the edit ingredient UI.
 */
public class EditIngredientFragment extends Fragment implements IEditIngredientView {
    /**
     * View binding for accessing the UI elements of the fragment.
     */
    FragmentEditItemsBinding binding;
    /**
     * Listener for handling fragment interactions, such as editing ingredients or completing the edit process.
     */
    Listener listener;

    /**
     * Creates a new instance of {@code EditIngredientFragment} with a specified listener.
     *
     * @param listener The listener to handle fragment interactions.
     * @return A new instance of {@code EditIngredientFragment}.
     */
    public static EditIngredientFragment newInstance(Listener listener) {
        EditIngredientFragment fragment = new EditIngredientFragment();
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
        binding = FragmentEditItemsBinding.inflate(inflater, container, false);
        setupQuantityField();
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

        binding.editButton.setOnClickListener(v -> onEditButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }


    /**
     * Handles the edit button click event to update an ingredient's quantity.
     * Validates the input and notifies the listener to perform the update.
     */
    public void onEditButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        double newQty;

 //       try {
            newQty = Double.parseDouble(binding.itemQuantityText.getText().toString().trim());
//        } catch (NumberFormatException e) {
//            Snackbar.make(binding.getRoot(), "Invalid quantity.", Snackbar.LENGTH_LONG).show();
//            return;
//        }

        if (listener != null && !name.isEmpty()) {
            listener.onEditIngredient(name, newQty);
        }
        clearInputs();
    }

    /**
     * Displays a Snackbar message indicating that the specified ingredient's quantity has been successfully updated.
     *
     * @param name The name of the updated ingredient.
     */
    public void showIngredientUpdateMessage(String name) {
        Snackbar.make(binding.getRoot(), "Updated quantity " + name, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Displays a Snackbar message indicating that the specified ingredient was not found in the pantry.
     *
     * @param name The name of the ingredient that was not found.
     */
    public void showIngredientNotFoundError(String name) {
        Snackbar.make(binding.getRoot(), name + " does not exist in your pantry", Snackbar.LENGTH_LONG).show();
    }


    /**
     * Handles the event when the Done button is clicked.
     * Notifies the listener that the editing process is complete and returns to the pantry view.
     */
    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onEditDone();
        }
        Snackbar.make(getView(), "Returning to Pantry", Snackbar.LENGTH_LONG).show();
    }


    /**
     * Restricts the quantity input to only accept numeric values.
     */
    private void setupQuantityField() {
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
     * Clears the input fields for the ingredient name and quantity.
     */
    private void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQuantityText.setText("");
    }

    /**
     * Displays a Snackbar with the specified error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}