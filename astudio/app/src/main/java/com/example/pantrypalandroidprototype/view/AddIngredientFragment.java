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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Fragment for adding ingredients to a list with dietary tags.
 * This fragment provides an interface to input ingredient details, including name, quantity, unit,
 * and optional dietary tags, and allows the user to add them to a list.
 */
public class AddIngredientFragment extends Fragment implements IAddIngredientView {

    FragmentAddItemsBinding binding;
    Listener listener;
    List<Ingredient> addedIngredients = new ArrayList<>();
    IngredientAdapter ingredientAdapter;

    /**
     * Creates a new instance of the fragment with a specified listener.
     *
     * @param listener A {@link Listener} that handles ingredient addition and completion events.
     * @return A new instance of {@code AddIngredientFragment}.
     */
    public static AddIngredientFragment newInstance(Listener listener) {
        AddIngredientFragment fragment = new AddIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Inflates the layout for this fragment and initializes the quantity field.
     *
     * @param inflater           The {@link LayoutInflater} used to inflate the layout.
     * @param container          The parent view that this fragment's UI will attach to.
     * @param savedInstanceState Saved state information for restoring the fragment.
     * @return The root {@link View} of the fragment.
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
     * Sets up the fragment's UI components and event listeners after the view has been created.
     *
     * @param view               The {@link View} of the fragment.
     * @param savedInstanceState Saved state information for restoring the fragment.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());

        ingredientAdapter = new IngredientAdapter(addedIngredients, getContext());
    }

    /**
     * Handles the logic when the "Add" button is clicked.
     * Validates the input fields, creates a new {@link Ingredient} object, and adds it to the list.
     * Notifies the adapter of changes and clears the input fields.
     */
    public void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String qtyString = binding.itemQtyText.getText().toString().trim();
        String unit = binding.itemUnitText.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Please fill in all fields.", Snackbar.LENGTH_LONG).show();
            return;
        }

        double qty = Double.parseDouble(qtyString);

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

        if (listener != null) {
            boolean addedSuccessfully = listener.onAddIngredient(name, qty, unit, tags);

            if (addedSuccessfully) {
                // Notify success
                Snackbar.make(binding.getRoot(), "Added " + name + " to pantry.", Snackbar.LENGTH_SHORT).show();
            } else {
                // Notify ingredient already exists
                Snackbar.make(binding.getRoot(), name + " already exists in the pantry.", Snackbar.LENGTH_LONG).show();
            }
        }

        // adapter update
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
                    return null;  // Accept valid numbers
                } else {
                    return "";  // Reject non-numeric input
                }
            }
        }});
    }

    /**
     * Clears all input fields and resets dietary tag checkboxes.
     */
    public void clearInputs() {
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
     * Handles the logic when the "Done" button is clicked.
     * Notifies the listener that the ingredient addition process is complete.
     */
    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onItemsDone();
        }
    }

    /**
     * Displays a "Done" message to the user (currently commented out).
     */
    public void showDoneMessage() {
        //Snackbar.make(binding.getRoot(), "Returning to Pantry", Snackbar.LENGTH_SHORT).show();
    }
}
