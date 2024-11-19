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
        void onEditIngredient(String name, double newQty);
        void onEditDone();
    }
}