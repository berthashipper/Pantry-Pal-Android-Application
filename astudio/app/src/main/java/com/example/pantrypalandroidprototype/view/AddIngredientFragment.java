package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pantrypalandroidprototype.databinding.FragmentAddItemsBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.IngredientAdapter;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddIngredientFragment extends Fragment {

    // Listener interface for button interactions
    public interface Listener {
        void onAddIngredient(String name, double qty, String unit, Set<String> tags);
        void onNavigateBackToPantry();
    }

    private FragmentAddItemsBinding binding;
    private Listener listener;
    private Set<Ingredient.dietary_tags> selectedTags = new HashSet<>();
    private List<Ingredient> addedIngredients = new ArrayList<>();
    private IngredientAdapter ingredientAdapter;

    public static AddIngredientFragment newInstance(Listener listener) {
        AddIngredientFragment fragment = new AddIngredientFragment();
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

        // Set up RecyclerView for displaying added ingredients
        ingredientAdapter = new IngredientAdapter(addedIngredients, getContext());
        binding.ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.ingredientsRecyclerView.setAdapter(ingredientAdapter);

        // Set up button click listener for adding ingredients
        binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());

        // Set up "Done" button click listener to return to pantry
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    private void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String qtyString = binding.itemQtyText.getText().toString().trim();
        String unit = binding.itemUnitText.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyString);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Quantity must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert dietary_tags to a Set<String> if needed
        Set<String> dietaryTags = new HashSet<>();
        for (Ingredient.dietary_tags tag : selectedTags) {
            dietaryTags.add(tag.toString());
        }

        // Create a new Ingredient object
        Ingredient newIngredient = new Ingredient(name, qty, unit, dietaryTags);
        addedIngredients.add(newIngredient);
        ingredientAdapter.notifyItemInserted(addedIngredients.size() - 1);

        // Clear the input fields
        clearInputs();
    }

    private void clearInputs() {
        binding.itemNameText.getText().clear();
        binding.itemQtyText.getText().clear();
        binding.itemUnitText.getText().clear();
    }

    private void onDoneButtonClicked() {
        // Notify listener to navigate back to pantry
        listener.onNavigateBackToPantry();
    }
}
