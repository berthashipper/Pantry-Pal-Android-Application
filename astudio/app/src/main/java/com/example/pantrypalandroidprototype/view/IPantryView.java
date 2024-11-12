package com.example.pantrypalandroidprototype.view;

import androidx.annotation.NonNull;
import com.example.pantrypalandroidprototype.model.Ingredient;

public interface IPantryView {

    interface Listener {
        void onAddIngredient(Ingredient ingredient, @NonNull final IPantryView view);
    }

    void updateDisplayOnDone(final double change);
}
