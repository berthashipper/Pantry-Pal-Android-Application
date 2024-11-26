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

import com.example.pantrypalandroidprototype.databinding.FragmentAddItemsBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.IngredientAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddIngredientFragment extends Fragment implements IAddIngredientView {

    FragmentAddItemsBinding binding;
    Listener listener;
    List<Ingredient> addedIngredients = new ArrayList<>();
    IngredientAdapter ingredientAdapter;

    public static AddIngredientFragment newInstance(Listener listener) {
        AddIngredientFragment fragment = new AddIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddItemsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        setupQuantityField();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());

        ingredientAdapter = new IngredientAdapter(addedIngredients, getContext());
        //binding.ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //binding.ingredientsRecyclerView.setAdapter(ingredientAdapter);
    }

    public void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String qtyString = binding.itemQtyText.getText().toString().trim();
        String unit = binding.itemUnitText.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Snackbar.make(getView(), "Please fill in all fields.", Snackbar.LENGTH_LONG).show();
            return;
        }

        // Check if the quantity is a valid number
        double qty;
//        try {
            qty = Double.parseDouble(qtyString);
//            if (qty <= 0) {
//                throw new NumberFormatException();
//            }
//        } catch (NumberFormatException e) {
//            Snackbar.make(getView(), "Invalid quantity. Please enter a valid number.", Snackbar.LENGTH_LONG).show();
//            return;
//        }

        Set<Ingredient.dietary_tags> tags = new HashSet<>();

        // Check dietary tags (checkboxes) and add corresponding tags
        if (binding.veganCheckbox.isChecked()) {
            tags.add(Ingredient.dietary_tags.VEGAN);
        }
        if (binding.kosherCheckbox.isChecked()) {
            tags.add(Ingredient.dietary_tags.KOSHER);
        }
        if (binding.glutenFreeCheckbox.isChecked()) {
            tags.add(Ingredient.dietary_tags.GLUTEN_FREE);
        }
        if (binding.halalCheckbox.isChecked()) {
            tags.add(Ingredient.dietary_tags.HALAL);
        }
        if (binding.nutFreeCheckbox.isChecked()) {
            tags.add(Ingredient.dietary_tags.NUT_FREE);
        }
        if (binding.vegetarianCheckbox.isChecked()) {
            tags.add(Ingredient.dietary_tags.VEGETARIAN);
        }
        if (binding.dairyFreeCheckbox.isChecked()) {
            tags.add(Ingredient.dietary_tags.DAIRY_FREE);
        }

        if (listener != null) listener.onAddIngredient(name, qty, unit, tags);

        // adapter update
        Ingredient newIngredient = new Ingredient(name, qty, unit, new HashSet<>());
        addedIngredients.add(newIngredient);
        ingredientAdapter.notifyItemInserted(addedIngredients.size() - 1);
        clearInputs();
    }


    /**
     * Restricts the quantity input to only accept numeric values.
     */
    private void setupQuantityField() {
        binding.itemQtyText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10), new InputFilter() {
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
        binding.itemQtyText.setText("");
        binding.itemUnitText.setText("");
        binding.veganCheckbox.setChecked(false);
        binding.kosherCheckbox.setChecked(false);
        binding.glutenFreeCheckbox.setChecked(false);
        binding.halalCheckbox.setChecked(false);
        binding.nutFreeCheckbox.setChecked(false);
        binding.vegetarianCheckbox.setChecked(false);
        binding.dairyFreeCheckbox.setChecked(false);

    }
    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onItemsDone();
        }
    }
    public void showDoneMessage() {
        Snackbar.make(getView(), "Done adding ingredients, returning to Pantry", Snackbar.LENGTH_LONG).show();
    }
}
