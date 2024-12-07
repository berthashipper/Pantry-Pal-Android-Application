package com.example.pantrypalandroidprototype.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.FragmentAddToGroceryListBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AddToGroceryListFragment extends Fragment implements IAddToGroceryListView {
    FragmentAddToGroceryListBinding binding;
    Listener listener;
    Map<Ingredient, Double> addedIngredients = new HashMap<>();
    IngredientAdapter ingredientAdapter;
    Map<Ingredient, Double> groceryList;
    boolean isDialogShown = false;

    public static AddToGroceryListFragment newInstance(Listener listener, Map<Ingredient, Double> groceryList) {
        AddToGroceryListFragment fragment = new AddToGroceryListFragment();
        Bundle args = new Bundle();
        args.putSerializable("groceryList", (Serializable) groceryList);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddToGroceryListBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        setupQuantityField();
        return rootView;
    }

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

    public void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String qtyString = binding.itemQtyText.getText().toString().trim();
        String unit = binding.itemUnitText.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Snackbar.make(getView(), "Please fill in all fields.", Snackbar.LENGTH_LONG).show();
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

    public void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQtyText.setText("");
        binding.itemUnitText.setText("");
    }

    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onItemsDone();
        }
    }

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
                    Snackbar.make(getView(), "Updated quantity of " + existingIngredient.getName() + ".", Snackbar.LENGTH_SHORT).show();

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

    public void showAddedIngredientMessage(String name) {
        Snackbar.make(getView(), "Added " + name + " to grocery list.", Snackbar.LENGTH_SHORT).show();
    }
}
