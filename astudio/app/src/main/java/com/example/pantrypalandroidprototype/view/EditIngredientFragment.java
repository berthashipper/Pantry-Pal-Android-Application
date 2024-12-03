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

public class EditIngredientFragment extends Fragment implements IEditIngredientView {
    FragmentEditItemsBinding binding;
    Listener listener;

    public static EditIngredientFragment newInstance(Listener listener) {
        EditIngredientFragment fragment = new EditIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditItemsBinding.inflate(inflater, container, false);
        setupQuantityField();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editButton.setOnClickListener(v -> onEditButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    public void onEditButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String quantityInput = binding.itemQuantityText.getText().toString().trim();

        if (quantityInput.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Invalid quantity.", Snackbar.LENGTH_LONG).show();
            return;
        }

        double newQty = Double.parseDouble(quantityInput);

        if (listener != null && !name.isEmpty()) {
            listener.onEditIngredient(this, name, newQty);
        } else if (name.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Item name cannot be empty.", Snackbar.LENGTH_LONG).show();
        }

        clearInputs();
    }

    public void showIngredientUpdateMessage(String name) {
        Snackbar.make(binding.getRoot(), "Updated quantity " + name, Snackbar.LENGTH_LONG).show();
    }


    public void showIngredientNotFoundError(String name) {
        Snackbar.make(binding.getRoot(), name + " does not exist in your pantry", Snackbar.LENGTH_LONG).show();
    }


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

    private void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQuantityText.setText("");
    }

    public void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}