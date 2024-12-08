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
         * Called when an ingredient is edited.
         *
         * @param editIngredientFragment The fragment instance that triggered the event.
         * @param name The name of the ingredient being edited.
         * @param newQty The new quantity of the ingredient.
         */
        void onEditIngredient(EditIngredientFragment editIngredientFragment, String name, double newQty);

        /**
         * Called when the editing process is completed.
         */
        void onEditDone();
    }
}
