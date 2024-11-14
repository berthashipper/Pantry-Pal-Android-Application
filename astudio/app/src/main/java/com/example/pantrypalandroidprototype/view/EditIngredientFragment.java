package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentAddItemsBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.util.HashSet;
import java.util.Set;

public class EditIngredientFragment extends Fragment implements IEditIngredientView {

    // Listener interface for button interactions
    public interface Listener {
        void onEditIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags);
    }

    private FragmentAddItemsBinding binding;
    private Listener listener;
    private Set<Ingredient.dietary_tags> selectedTags = new HashSet<>();

    public static EditIngredientFragment newInstance(Listener listener) {
        EditIngredientFragment fragment = new EditIngredientFragment();
        fragment.listener = listener; // Set listener
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddItemsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up button click listener
        binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());
    }

    private void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String unit = binding.itemUnitText.getText().toString().trim();
        String qtyString = binding.itemQtyText.getText().toString().trim();

        // Validate input fields
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Please enter an ingredient name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (unit.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a unit", Toast.LENGTH_SHORT).show();
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyString);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Quantity must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Notify listener with the data
        if (listener != null) {
            listener.onEditIngredient(name, qty, unit, selectedTags);
        }
        clearInputs();
    }

    private void clearInputs() {
        binding.itemNameText.getText().clear();
        binding.itemQtyText.getText().clear();
        binding.itemUnitText.getText().clear();
        selectedTags.clear(); // Clear dietary tags selection
    }

    @Override
    public void updatePantryDisplay(@NonNull Pantry pantry) {
        Toast.makeText(getContext(), "Pantry updated with " + pantry.getNPantryItems() + " items.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View getRootView() {
        return binding.getRoot();
    }
}
