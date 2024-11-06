package com.example.pantrypalandroidprototype.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.ActivityMainBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;


import java.util.HashSet;
import java.util.Set;


public class AddIngredientView implements IAddIngredientView {

    private final ActivityMainBinding binding;
    private final Listener listener;
    private final Set<Ingredient.dietary_tags> selectedTags = new HashSet<>();

    public AddIngredientView(Context context, Listener listener) {
        this.listener = listener;
        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(context));

        this.binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());
//        setupTagDropdown(context);
    }

    @Override
    public View getRootView() {
        return binding.getRoot();
    }

    @Override
    public void updatePantryDisplay(@NonNull final Pantry pantry) {
        Toast.makeText(binding.getRoot().getContext(), "Pantry updated with " + pantry.getNPantryItems() + " items.", Toast.LENGTH_SHORT).show();
    }

    private void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString();
        String qtyString = binding.itemQtyText.getText().toString();
        String unit = binding.itemUnitText.getText().toString();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Toast.makeText(binding.getRoot().getContext(), R.string.missing_item_fields_error, Toast.LENGTH_SHORT).show();
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyString);
        } catch (NumberFormatException e) {
            Toast.makeText(binding.getRoot().getContext(), R.string.missing_item_fields_error, Toast.LENGTH_SHORT).show();
            return;
        }

        listener.onAddIngredient(name, qty, unit, new HashSet<>(selectedTags));
    }




}
