package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.util.HashSet;

public class PantryFragment extends Fragment implements IPantryView {

    private Pantry pantry;
    private TextView pantryContentsTextView;
    private EditText ingredientNameEditText, ingredientQuantityEditText, ingredientUnitEditText;
    private Button addButton, showPantryButton;

    private Listener listener;

    public interface Listener {
        void onAddIngredient(Ingredient ingredient);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pantry, container, false);

        // Initialize the pantry model
        pantry = new Pantry();

        // Initialize UI elements
        pantryContentsTextView = view.findViewById(R.id.pantryContentsTextView);
        ingredientNameEditText = view.findViewById(R.id.ingredientNameEditText);
        ingredientQuantityEditText = view.findViewById(R.id.ingredientQuantityEditText);
        ingredientUnitEditText = view.findViewById(R.id.ingredientUnitEditText);
        addButton = view.findViewById(R.id.addButton);
        showPantryButton = view.findViewById(R.id.showPantryButton);

        // Set up button listeners
        addButton.setOnClickListener(v -> addIngredientToPantry());
        showPantryButton.setOnClickListener(v -> showPantryContents());

        return view;
    }

    private void addIngredientToPantry() {
        String name = ingredientNameEditText.getText().toString().trim();
        String quantityStr = ingredientQuantityEditText.getText().toString().trim();
        String unit = ingredientUnitEditText.getText().toString().trim();

        if (!name.isEmpty() && !quantityStr.isEmpty() && !unit.isEmpty()) {
            double quantity = Double.parseDouble(quantityStr);
            Ingredient ingredient = new Ingredient(name, quantity, unit, new HashSet<>());
            pantry.add_ingredient(ingredient);

            if (listener != null) {
                listener.onAddIngredient(ingredient);
            }

            updateDisplayOnDone(pantry.getNPantryItems());
            clearInputFields();
        }
    }

    private void showPantryContents() {
        pantryContentsTextView.setText(pantry.toString());
    }

    private void clearInputFields() {
        ingredientNameEditText.setText("");
        ingredientQuantityEditText.setText("");
        ingredientUnitEditText.setText("");
    }

    @Override
    public void updateDisplayOnDone(double change) {
        pantryContentsTextView.setText("Pantry now has " + (int) change + " items.");
    }
}
