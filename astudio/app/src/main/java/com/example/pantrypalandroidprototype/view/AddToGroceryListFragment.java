package com.example.pantrypalandroidprototype.view;

import android.app.AlertDialog;
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
import com.example.pantrypalandroidprototype.databinding.FragmentAddToGroceryListBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Fragment for adding ingredients to a grocery list.
 * This fragment allows users to add ingredients with their quantities and units to a grocery list.
 * It supports handling duplicate ingredients, updating their quantities, and notifying a listener of completed actions.
 */
public class AddToGroceryListFragment extends Fragment implements IAddToGroceryListView {
    FragmentAddToGroceryListBinding binding;
    Listener listener;
    Map<Ingredient, Double> addedIngredients = new HashMap<>();
    IngredientAdapter ingredientAdapter;
    Map<Ingredient, Double> groceryList;
    boolean isDialogShown = false;

    /**
     * Creates a new instance of the fragment with a specified listener and grocery list.
     *
     * @param listener    A {@link Listener} to handle grocery list-related events.
     * @param groceryList The initial grocery list to modify.
     * @return A new instance of {@code AddToGroceryListFragment}.
     */
    public static AddToGroceryListFragment newInstance(Listener listener, Map<Ingredient, Double> groceryList) {
        AddToGroceryListFragment fragment = new AddToGroceryListFragment();
        Bundle args = new Bundle();
        args.putSerializable("groceryList", (Serializable) groceryList);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Inflates the fragment's layout and sets up the quantity input field.
     *
     * @param inflater           The {@link LayoutInflater} used to inflate the layout.
     * @param container          The parent view that this fragment's UI will attach to.
     * @param savedInstanceState Saved state information for restoring the fragment.
     * @return The root {@link View} of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddToGroceryListBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        setupQuantityField();
        return rootView;
    }

    /**
     * Configures the fragment's UI components and sets up event listeners for user actions.
     *
     * @param view               The {@link View} of the fragment.
     * @param savedInstanceState Saved state information for restoring the fragment.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            groceryList = (Map<Ingredient, Double>) getArguments().getSerializable("groceryList");
        }

        binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());

        ingredientAdapter = new IngredientAdapter(new ArrayList<>(addedIngredients.keySet()), getContext());
    }

    /**
     * Handles the addition of a new ingredient to the grocery list.
     * Validates the input fields, notifies the listener, and clears the input fields upon success.
     */
    public void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String qtyString = binding.itemQtyText.getText().toString().trim();
        String unit = binding.itemUnitText.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Please fill in all fields.", Snackbar.LENGTH_LONG).show();
            return;
        }

        double qty = Double.parseDouble(qtyString);

        if (listener != null) {
            listener.onAddIngredientToGroceryList(name, qty, unit);
            Log.d("AddToGroceryListFragment", "Listener called successfully.");
        }

        clearInputs();
    }

    /**
     * Restricts the quantity input to only accept numeric values.
     */
    public void setupQuantityField() {
        binding.itemQtyText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals("") || source.toString().matches("[0-9]*\\.?[0-9]*")) {
                    return null;
                } else {
                    return "";
                }
            }
        }});
    }

    /**
     * Clears all input fields related to adding an ingredient.
     */
    public void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQtyText.setText("");
        binding.itemUnitText.setText("");
    }


    /**
     * Handles the finalization of the grocery list and notifies the listener.
     */
    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onItemsDone();
        }
    }

    /**
     * Displays a dialog to update the quantity of an existing ingredient.

     * If the ingredient already exists in the grocery list, this dialog allows the user to update its quantity.
     * Prevents multiple dialogs from showing simultaneously.

     * @param existingIngredient The ingredient already in the grocery list.
     * @param newQty             The new quantity to update.
     */
    public void showUpdateQuantityDialog(Ingredient existingIngredient, double newQty) {
        // Check if the dialog is already showing to prevent a loop
        if (isDialogShown) {
            return; // Skip the dialog if it’s already showing
        }

        isDialogShown = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Ingredient Already on List")
                .setMessage("The ingredient " + existingIngredient.getName() + " is already on your grocery list. Would you like to update the quantity?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Update the grocery list with the new quantity
                    groceryList.put(existingIngredient, newQty);
                    Snackbar.make(binding.getRoot(), "Updated quantity of " + existingIngredient.getName() + ".", Snackbar.LENGTH_SHORT).show();

                    // Inform the controller that the quantity has been updated
                    if (listener != null) {
                        listener.onAddIngredientToGroceryList(existingIngredient.getName(), newQty, existingIngredient.getUnit());
                    }

                    // Dismiss the dialog after the update
                    dialog.dismiss();
                    isDialogShown = false;  // Reset the flag after the dialog is dismissed
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Dismiss the dialog for the "No" option
                    dialog.dismiss();
                    isDialogShown = false;  // Reset the flag when the dialog is canceled
                })
                .create()
                .show();
    }

    /**
     * Displays a message to notify the user that an ingredient has been added to the grocery list.
     *
     * @param name The name of the ingredient that was added.
     */
    public void showAddedIngredientMessage(String name) {
        Snackbar.make(binding.getRoot(), "Added " + name + " to grocery list.", Snackbar.LENGTH_SHORT).show();
    }
}
