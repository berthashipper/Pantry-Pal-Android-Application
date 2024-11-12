package com.example.pantrypalandroidprototype.view;

import androidx.annotation.NonNull;

import com.example.pantrypalandroidprototype.model.Ingredient;

public interface IPantryView {

    /**
     * Interface that classes interested in being notified of events happening
     * to the view should implement.
     */
    interface Listener {
        void onAddIngredient(Ingredient ingredient, @NonNull final IPantryView view);
    }

    void updateDisplayOnDone(final double change);

}
