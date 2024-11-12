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

public class EditIgredientFragment extends Fragment implements IEditingredientView {
    // Listener interface for button interactions
    public interface Listener {
        void onEditIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags);
    }

    private FragmentAddItemsBinding binding;
    private Listener listener;
    private Set<Ingredient.dietary_tags> selectedTags = new HashSet<>();

    public static EditIngredientFragment newInstance(Listener listener) {
        EditIngredientFragment fragment = new EditIngredientFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);  // Add other arguments here if needed
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

        double qty;
        try {
            qty = Double.parseDouble(qtyString);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Quantity must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Notify listener with the data
        listener.onAddIngredient(name, qty, unit, selectedTags);
        clearInputs();
    }

    private void clearInputs() {
        binding.itemQtyText.getText().clear();
    }

    public void updatePantryDisplay(@NonNull final Pantry pantry) {
        Toast.makeText(getContext(), "Pantry updated with " + pantry.getNPantryItems() + " items.", Toast.LENGTH_SHORT).show();
    }
}