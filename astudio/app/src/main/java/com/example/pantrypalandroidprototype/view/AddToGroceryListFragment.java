package com.example.pantrypalandroidprototype.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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

    public static AddToGroceryListFragment newInstance(Listener listener) {
        AddToGroceryListFragment fragment = new AddToGroceryListFragment();
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

        binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());

        ingredientAdapter = new IngredientAdapter(new ArrayList<>(addedIngredients.keySet()), getContext());
    }

    public void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String qtyString = binding.itemQtyText.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Snackbar.make(getView(), "Please fill in all fields.", Snackbar.LENGTH_LONG).show();
            return;
        }

        double qty = Double.parseDouble(qtyString);

        if (listener != null) {
            listener.onAddIngredientToGroceryList(name, qty);
        }

        Ingredient newIngredient = new Ingredient(name, qty, "unit_placeholder", new HashSet<>());
        addedIngredients.put(newIngredient, qty);
        Snackbar.make(getView(), "Added " + newIngredient.getName() + " to grocery list.", Snackbar.LENGTH_SHORT).show();
        ingredientAdapter.notifyDataSetChanged();
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
    }

    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onItemsDone();
        }
    }

    public void showDoneMessage() {
        Snackbar.make(getView(), "Returning to Grocery List", Snackbar.LENGTH_SHORT).show();
    }
}
