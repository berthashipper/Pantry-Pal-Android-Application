package com.example.pantrypalandroidprototype.view;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.util.Set;

/**
 * Interface that defines the methods for a view that allows a user to edit ingredients.
 */
public interface IEditIngredientView {

    /**
     * Listener interface for handling ingredient editing events.
     */
    interface Listener {
        /**
         * Called when an ingredient is to be edited in the pantry.
         * @param name          Name of the ingredient
         * @param qty           Quantity of the ingredient
         * @param unit          Unit of measurement
         * @param dietary_tags  Set of dietary tags for the ingredient
         */
        void onEditIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> dietary_tags);

        /**
         * Called when all items are done being edited.
         */
        void onItemsDone();
    }

    /**
     * Updates the pantry display with the current list of items.
     * @param pantry The pantry object containing updated items
     */
    void updatePantryDisplay(@NonNull Pantry pantry);

    /**
     * Returns the root view of the fragment.
     * @return Root view of the fragment
     */
    View getRootView();
}