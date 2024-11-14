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

public class DeleteIngredientFragment extends Fragment implements IDeleteIngredientView {

    private FragmentAddItemsBinding binding;
    private Listener listener;
    private Set<Ingredient.dietary_tags> selectedTags = new HashSet<>();

    /**
     * Listener interface for button interactions.
     */
    public interface Listener {
        void onDeleteIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags);
    }

    /**
     * Static factory method to create a new instance of this fragment.
     */
    public static DeleteIngredientFragment newInstance(Listener listener) {
        DeleteIngredientFragment fragment = new DeleteIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
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

    /**
     * Handle the add button click to delete an ingredient.
     */
    private void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        double qty = parseQuantity(binding.itemQtyText.getText().toString().trim());
        String unit = binding.itemUnitText.getText().toString().trim();

        gatherSelectedDietaryTags();

        if (listener != null && !name.isEmpty() && qty > 0) {
            listener.onDeleteIngredient(name, qty, unit, selectedTags);
        }
        clearInputs();
    }

    /**
     * Gathers selected dietary tags from the UI.
     */
    private void gatherSelectedDietaryTags() {
        selectedTags.clear();

        if (binding.veganCheckbox.isChecked()) {
            selectedTags.add(Ingredient.dietary_tags.VEGAN);
        }
        if (binding.glutenFreeCheckbox.isChecked()) {
            selectedTags.add(Ingredient.dietary_tags.GLUTEN_FREE);
        }
    }

    private double parseQuantity(String qtyStr) {
        try {
            return Double.parseDouble(qtyStr);
        } catch (NumberFormatException e) {
            return 0; // Default to 0 if parsing fails
        }
    }

    @Override
    public void updatePantryDisplay(@NonNull final Pantry pantry) {
        Toast.makeText(getContext(), "Pantry updated with " + pantry.getNPantryItems() + " items.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View getRootView() {
        return binding.getRoot();
    }

    private void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQtyText.setText("");
        binding.itemUnitText.setText("");
        binding.veganCheckbox.setChecked(false);
        binding.glutenFreeCheckbox.setChecked(false);
    }
}
