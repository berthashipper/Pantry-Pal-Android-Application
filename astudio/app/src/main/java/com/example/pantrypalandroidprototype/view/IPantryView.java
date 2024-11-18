package com.example.pantrypalandroidprototype.view;

import androidx.annotation.NonNull;
import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.List;

public interface IPantryView {

    void updatePantry(List<Ingredient> ingredients);

    interface Listener {
        void onAddIngredient(Ingredient ingredient, @NonNull final IPantryView view);
    }

    void updateDisplayOnDone(final double change);
}
