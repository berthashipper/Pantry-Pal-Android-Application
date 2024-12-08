package com.example.pantrypalandroidprototype.view;

import android.view.View;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
/**
 * Interface that defines the methods for a view that displays and interacts with a user's grocery list.
 */
public interface IGroceryListView {

    /**
     * Called when an ingredient is edited in the grocery list.
     *
     * @param ingredient The ingredient that needs to be edited.
     */
    void onEditIngredientGroceryList(Ingredient ingredient);

    /**
     * Listener interface for handling events in the grocery list view.
     */
    interface Listener {

        /**
         * Called when the user wants to add ingredients to the grocery list menu.
         */
        void onAddIngredientsToGroceryListMenu();

        /**
         * Called when an ingredient is edited in the grocery list.
         *
         * @param ingredient The ingredient that needs to be edited.
         */
        void onEditIngredientGroceryList(Ingredient ingredient);

        /**
         * Called when the user wants to clear the entire shopping list.
         */
        void onClearShoppingList();

        /**
         * Called when an ingredient is removed from the grocery list.
         *
         * @param ingredient The ingredient to be removed.
         */
        void onRemoveIngredient(Ingredient ingredient);
    }
}
