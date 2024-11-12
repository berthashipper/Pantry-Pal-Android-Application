package com.example.pantrypalandroidprototype.view;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.pantrypalandroidprototype.databinding.FragmentAddItemsBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.google.android.material.snackbar.Snackbar;
import java.util.HashSet;
import java.util.Set;

public class AddIngredientView {

    // Listener interface for button interactions
    public interface Listener {
        void onAddIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags);
    }

    FragmentAddItemsBinding binding;
    Listener listener;
    Set<Ingredient.dietary_tags> selectedTags = new HashSet<>();

    public AddIngredientView(Context context, Listener listener) {
        this.listener = listener;
        this.binding = FragmentAddItemsBinding.inflate(LayoutInflater.from(context));

        // Set up button click listener
        this.binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());
    }

    public View getRootView() {
        return binding.getRoot();
    }

    private void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String qtyString = binding.itemQtyText.getText().toString().trim();
        String unit = binding.itemUnitText.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Toast.makeText(binding.getRoot().getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyString);
        } catch (NumberFormatException e) {
            Toast.makeText(binding.getRoot().getContext(), "Quantity must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Notify listener with the data
        listener.onAddIngredient(name, qty, unit, selectedTags);
        clearInputs();
    }

    private void clearInputs() {
        binding.itemNameText.getText().clear();
        binding.itemQtyText.getText().clear();
        binding.itemUnitText.getText().clear();
    }

    public void updatePantryDisplay(@NonNull final Pantry pantry) {
        Toast.makeText(binding.getRoot().getContext(), "Pantry updated with " + pantry.getNPantryItems() + " items.", Toast.LENGTH_SHORT).show();
    }
}
