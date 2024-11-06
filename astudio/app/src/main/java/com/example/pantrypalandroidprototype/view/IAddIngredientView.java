package com.example.pantrypalandroidprototype.view;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.util.Set;

/**
 * Interface that defines the methods for a view that allows a user to add items to a sale.
 */
public interface IAddIngredientView {

    /**
     * Interface that classes interested in being notified of events happening
     * to the view should implement.
     */
    interface Listener {
        /**
         * Called when an ingredient is to be added to the pantry.
         */
        void onAddIngredient(final String name, final double qty, final String unit, final Set<Ingredient.dietary_tags> dietary_tags);
    }

    void updatePantryDisplay(@NonNull final Pantry pantry);

    /**
     * Returns top-level graphical element.
     * @return top-level graphical element
     */
    View getRootView();

}
