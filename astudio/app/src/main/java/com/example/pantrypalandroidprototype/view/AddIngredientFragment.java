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

/**
 * The {@code AddIngredientFragment} class represents a fragment for adding ingredients to a list.
 * It implements the {@link IAddIngredientView} interface and provides functionality to input
 * ingredient details, validate data, and update the ingredient list.
 */
public class AddIngredientFragment extends Fragment implements IAddIngredientView {
    /**
     * View binding for accessing the UI elements of the fragment.
     */
    FragmentAddItemsBinding binding;

    /**
     * Listener for handling interactions with the fragment.
     */
    Listener listener;
    List<Ingredient> addedIngredients = new ArrayList<>();

    /**
     * Adapter for displaying the list of added ingredients.
     */
    IngredientAdapter ingredientAdapter;

    /**
     * Creates a new instance of {@code AddIngredientFragment} with a specified listener.
     *
     * @param listener The listener to handle fragment interactions.
     * @return A new instance of {@code AddIngredientFragment}.
     */
    public static AddIngredientFragment newInstance(Listener listener) {
        AddIngredientFragment fragment = new AddIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Called to create the fragment's view hierarchy.
     *
     * @param inflater  The {@link LayoutInflater} object for inflating views.
     * @param container The parent view group for the fragment's UI.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     * @return The root view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddItemsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        setupQuantityField();
        return rootView;
    }

    /**
     * Called immediately after the fragment's view has been created.
     *
     * @param view The fragment's root view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());

        ingredientAdapter = new IngredientAdapter(addedIngredients, getContext());
        //binding.ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //binding.ingredientsRecyclerView.setAdapter(ingredientAdapter);
    }


    /**
     * Handles the "Add Ingredient" button click event.
     * Validates user input, creates a new ingredient, and updates the ingredient list.
     */
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

    /**
     * Clears all input fields and resets checkboxes.
     */
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

    /**
     * Handles the "Done" button click event.
     * Notifies the listener that ingredient addition is complete.
     */
    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onItemsDone();
        }
    }

    /**
     * Displays a completion message using a {@link Snackbar}.
     */
    public void showDoneMessage() {
        Snackbar.make(getView(), "Done adding ingredients, returning to Pantry", Snackbar.LENGTH_LONG).show();
    }
}
