package com.example.pantrypalandroidprototype.view;


/**
 * Interface that defines the methods for a view that allows a user to edit ingredients in recipe.
 */
public interface IEditRecipeIngredientView {

    /**
     * Listener interface for handling ingredient editing events.
     */
    interface Listener {
        void onEditRecipeIngredient(String name, double newQty);
        void onEditRecipeDone();

    }
}
