package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.Ingredient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PantryFragment extends Fragment implements IPantryView {

    List<Ingredient> pantryIngredients = new ArrayList<>();
    TextView pantryContentsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pantry, container, false);
        pantryContentsTextView = rootView.findViewById(R.id.pantryContentsTextView);

        // Handle the arguments passed to the fragment
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("addedIngredients")) {
            Serializable serializableIngredients = bundle.getSerializable("addedIngredients");
            if (serializableIngredients instanceof List<?>) {
                List<Ingredient> newIngredients = (List<Ingredient>) serializableIngredients;
                if (newIngredients != null) {
                    pantryIngredients.addAll(newIngredients);
                }
            }
        }

        displayPantryContents();
        return rootView;
    }

    public void displayPantryContents() {
        StringBuilder pantryString = new StringBuilder();
        for (Ingredient ingredient : pantryIngredients) {
            pantryString.append(ingredient.getName()).append(" - ")
                    .append(ingredient.getQuantity()).append(" ")
                    .append(ingredient.getUnit()).append("\n");
        }
        pantryContentsTextView.setText(pantryString.toString());
    }

    @Override
    public void updatePantry(List<Ingredient> ingredients) {
        pantryIngredients.addAll(ingredients);
        displayPantryContents();
    }

    @Override
    public void updateDisplayOnDone(double change) {

    }
}
