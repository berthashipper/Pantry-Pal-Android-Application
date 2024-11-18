package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.os.Parcelable;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PantryFragment extends Fragment implements IPantryView {

    Pantry pantry;
    List<Ingredient> pantryIngredients = new ArrayList<>();
    TextView pantryContentsTextView;
    EditText ingredientNameEditText, ingredientQuantityEditText, ingredientUnitEditText;
    Button viewPantryButton;

    Listener listener;

    public interface Listener {
        void onAddIngredient(Ingredient ingredient);
        void onNavigateBackToPantry();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pantry, container, false);

        // Initialize the TextView
        TextView pantryTextView = rootView.findViewById(R.id.pantryContentsTextView);

        viewPantryButton = rootView.findViewById(R.id.viewPantryButton);
        viewPantryButton.setOnClickListener(v -> showPantryContents());

        if (pantryIngredients == null) {
            pantryIngredients = new ArrayList<>();
        }

        // Display pantry ingredients
        StringBuilder pantryString = new StringBuilder();
        for (Ingredient ingredient : pantryIngredients) {
            pantryString.append(ingredient.getName()).append("\n");
        }
        pantryTextView.setText(pantryString.toString());

        return rootView;
    }

    public void addIngredientToPantry(Ingredient ingredient) {
        String name = ingredientNameEditText.getText().toString().trim();
        String quantityStr = ingredientQuantityEditText.getText().toString().trim();
        String unit = ingredientUnitEditText.getText().toString().trim();

        if (!name.isEmpty() && !quantityStr.isEmpty() && !unit.isEmpty()) {
            double quantity = Double.parseDouble(quantityStr);
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

    // Method to update the pantry with added ingredients
    public void updatePantry(List<Ingredient> addedIngredients) {
        for (Ingredient ingredient : addedIngredients) {
            pantry.add_ingredient(ingredient);
        }
        pantryContentsTextView.setText(pantry.toString());
    }
}
