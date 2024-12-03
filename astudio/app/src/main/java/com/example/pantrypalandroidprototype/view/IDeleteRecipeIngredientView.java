package com.example.pantrypalandroidprototype.view;

public interface IDeleteRecipeIngredientView {

/**
 * Listener interface for handling ingredient deleting events.
 */
interface Listener {
    void onDeleteRecipeIngredient(String name);
    void onDeleteRecipeDone();

}
}
